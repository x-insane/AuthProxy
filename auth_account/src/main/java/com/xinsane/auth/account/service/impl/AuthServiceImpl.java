package com.xinsane.auth.account.service.impl;

import com.google.gson.Gson;
import com.xinsane.auth.account.entity.AuthGlobalEntity;
import com.xinsane.auth.account.entity.AuthGlobalUserEntity;
import com.xinsane.auth.account.repository.AuthRepository;
import com.xinsane.auth.account.repository.AuthUserRepository;
import com.xinsane.auth.account.repository.UserRepository;
import com.xinsane.auth.account.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthRepository authRepository;
    private final AuthUserRepository authUserRepository;
    private final UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(AuthRepository authRepository, AuthUserRepository authUserRepository,
                           UserRepository userRepository) {
        this.authRepository = authRepository;
        this.authUserRepository = authUserRepository;
        this.userRepository = userRepository;
    }

    @Override public void addAuth(int userId, int authId) throws AuthIdNotFoundException, ParentAuthNotContainsException {
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

    @Override public void addAuthWithoutCheck(int userId, int authId) {
        if (authUserRepository.countByUserIdAndAuthId(userId, authId) == 0) {
            authUserRepository.save(new AuthGlobalUserEntity()
                    .setAuthId(authId)
                    .setUserId(userId)
            );
        }
    }
}
