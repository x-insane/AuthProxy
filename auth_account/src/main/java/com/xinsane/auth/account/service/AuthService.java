package com.xinsane.auth.account.service;

import com.xinsane.auth.account.entity.AuthGlobalEntity;

public interface AuthService {
    /**
     * 给用户添加权限，同时检查权限前提与权限包含
     * @param userId 用户id
     * @param authId 权限id
     * @throws AuthIdNotFoundException 所给authId不存在
     * @throws ParentAuthNotContainsException 权限前提未满足
     */
    void addAuth(int userId, int authId) throws AuthIdNotFoundException, ParentAuthNotContainsException;

    /**
     * 给用户添加权限，不检查权限前提与权限包含
     * @param userId 用户id
     * @param authId 权限id
     */
    void addAuthWithoutCheck(int userId, int authId);

    class AuthIdNotFoundException extends Exception {
        private final int authId;
        public int getAuthId() {
            return authId;
        }
        public AuthIdNotFoundException(int authId) {
            super("权限id <" + authId + ">不存在");
            this.authId = authId;
        }
    }

    class ParentAuthNotContainsException extends Exception {
        private final AuthGlobalEntity sourceAuth, parentAuth;
        public AuthGlobalEntity getSourceAuth() {
            return sourceAuth;
        }
        public AuthGlobalEntity getParentAuth() {
            return parentAuth;
        }
        public ParentAuthNotContainsException(AuthGlobalEntity sourceAuth, AuthGlobalEntity parentAuth) {
            super("权限<" + sourceAuth.getAuthName() + ">的前提权限<" + parentAuth.getAuthName() + ">未满足");
            this.sourceAuth = sourceAuth;
            this.parentAuth = parentAuth;
        }
    }
}
