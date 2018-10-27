package com.xinsane.auth.account.repository;

import com.xinsane.auth.account.entity.AuthSiteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SiteRepository extends CrudRepository<AuthSiteEntity, Integer> {
    AuthSiteEntity queryFirstBySiteId(int siteId);

    @Query("FROM AuthSiteEntity")
    List<AuthSiteEntity> queryAll();
}
