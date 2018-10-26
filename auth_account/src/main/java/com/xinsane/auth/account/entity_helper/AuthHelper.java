package com.xinsane.auth.account.entity_helper;

import com.google.gson.Gson;
import com.xinsane.auth.account.entity.AuthGlobalEntity;
import com.xinsane.auth.account.entity.AuthGlobalUserEntity;
import com.xinsane.auth.account.repository.AuthRepository;
import com.xinsane.auth.account.repository.AuthUserRepository;
import com.xinsane.auth.account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthHelper {

    private final AuthRepository authRepository;
    private final AuthUserRepository authUserRepository;
    private final UserRepository userRepository;

    @Autowired
    public AuthHelper(AuthRepository authRepository, AuthUserRepository authUserRepository, UserRepository userRepository) {
        this.authRepository = authRepository;
        this.authUserRepository = authUserRepository;
        this.userRepository = userRepository;
    }

    /**
     * 给用户添加权限，同时检查权限前提与权限包含
     * @param userId 用户id
     * @param authId 权限id
     * @throws AuthIdNotFoundException 所给authId不存在
     * @throws ParentAuthNotContainsException 权限前提未满足
     */
    public void addAuth(int userId, int authId) throws AuthIdNotFoundException, ParentAuthNotContainsException {
        AuthGlobalEntity auth = authRepository.findFirstByAuthId(authId);
        if (auth == null)
            throw new AuthIdNotFoundException(authId);

        // 权限前提
        Integer[] parents = new Gson().fromJson(auth.getAuthParent(), Integer[].class);
        List<Integer> list = userRepository.getUserAuthId(userId);
        for (Integer parent : parents) {
            if (!list.contains(parent))
                throw new ParentAuthNotContainsException(auth, authRepository.findFirstByAuthId(parent));
        }

        // 权限包含
        Integer[] children = new Gson().fromJson(auth.getAuthChildren(), Integer[].class);
        for (Integer child : children)
            addAuthWithoutCheck(userId, child);

        addAuthWithoutCheck(userId, authId);
    }

    /**
     * 给用户添加权限，不检查权限前提与权限包含
     * @param userId 用户id
     * @param authId 权限id
     */
    public void addAuthWithoutCheck(int userId, int authId) {
        if (authUserRepository.countByUserIdAndAuthId(userId, authId) == 0) {
            authUserRepository.save(new AuthGlobalUserEntity()
                    .setAuthId(authId)
                    .setUserId(userId)
            );
        }
    }

    public static class AuthIdNotFoundException extends Exception {
        private final int authId;

        public int getAuthId() {
            return authId;
        }

        AuthIdNotFoundException(int authId) {
            super("权限id <" + authId + ">不存在");
            this.authId = authId;
        }
    }

    public static class ParentAuthNotContainsException extends Exception {
        private final AuthGlobalEntity sourceAuth, parentAuth;

        public AuthGlobalEntity getSourceAuth() {
            return sourceAuth;
        }

        public AuthGlobalEntity getParentAuth() {
            return parentAuth;
        }

        ParentAuthNotContainsException(AuthGlobalEntity sourceAuth, AuthGlobalEntity parentAuth) {
            super("权限<" + sourceAuth.getAuthName() + ">的前提权限<" + parentAuth.getAuthName() + ">未满足");
            this.sourceAuth = sourceAuth;
            this.parentAuth = parentAuth;
        }
    }
}
