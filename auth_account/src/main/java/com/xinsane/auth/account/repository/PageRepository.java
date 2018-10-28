package com.xinsane.auth.account.repository;

import com.xinsane.auth.account.entity.AuthPageEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PageRepository extends CrudRepository<AuthPageEntity, Integer> {
    AuthPageEntity queryFirstByPageId(int pageId);
    boolean existsBySiteId(int  siteId);

    @Query("FROM AuthPageEntity page WHERE page.siteId=?1")
    List<AuthPageEntity> queryAllBySiteId(int siteId);
}
