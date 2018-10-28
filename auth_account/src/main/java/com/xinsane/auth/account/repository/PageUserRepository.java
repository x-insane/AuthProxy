package com.xinsane.auth.account.repository;

import com.xinsane.auth.account.entity.AuthPageUserEntity;
import org.springframework.data.repository.CrudRepository;

public interface PageUserRepository extends CrudRepository<AuthPageUserEntity, Integer> {
    AuthPageUserEntity queryFirstByUserIdAndPageId(int userId, int pageId);
    boolean existsByPageId(int pageId);
}
