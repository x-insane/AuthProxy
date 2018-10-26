package com.xinsane.auth.account.repository;

import com.xinsane.auth.account.entity.AuthGlobalUserEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthUserRepository extends CrudRepository<AuthGlobalUserEntity, Integer> {
    long countByUserIdAndAuthId(int userId, int authId);
}
