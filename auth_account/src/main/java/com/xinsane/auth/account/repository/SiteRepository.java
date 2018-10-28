package com.xinsane.auth.account.repository;

import com.xinsane.auth.account.entity.AuthSiteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SiteRepository extends CrudRepository<AuthSiteEntity, Integer> {
    AuthSiteEntity queryFirstBySiteId(int siteId);

    @Query("FROM AuthSiteEntity")
    List<AuthSiteEntity> queryAll();

    /**
     * 获取用户管理的所有站点
     * @param userId 用户id
     * @return 用户管理的所有站点列表
     */
    @Query("FROM AuthSiteEntity site " +
            "INNER JOIN AuthSiteUserEntity su " +
            "ON su.userId=?1 AND su.manageable=true AND site.siteId=su.siteId")
    List<AuthSiteEntity> queryUserManagedSites(int userId);
}
