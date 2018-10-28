package com.xinsane.auth.account.repository;

import com.xinsane.auth.account.entity.AuthSiteUserEntity;
import org.springframework.data.repository.CrudRepository;

public interface SiteUserRepository extends CrudRepository<AuthSiteUserEntity, Integer> {
    AuthSiteUserEntity queryFirstByUserIdAndSiteId(int userId, int siteId);
    boolean  existsBySiteId(int siteIId);
    boolean existsByUserIdAndSiteIdAndManageableIsTrue(int userId, int siteId);
}
