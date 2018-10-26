package com.xinsane.auth.account.repository;

import com.xinsane.auth.account.entity.AuthSiteEntity;
import org.springframework.data.repository.CrudRepository;

public interface SiteRepository extends CrudRepository<AuthSiteEntity, Integer> {
    AuthSiteEntity queryFirstBySiteId(int siteId);
}
