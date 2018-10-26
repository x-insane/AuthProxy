package com.xinsane.auth.account.repository;

import com.xinsane.auth.account.entity.AuthGlobalEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AuthRepository extends CrudRepository<AuthGlobalEntity, Integer> {
    AuthGlobalEntity findFirstByAuthId(int authId);

    /**
     * 根据权限key返回权限id
     * @param authKey 权限key
     * @return 权限存在时返回权限id，否则返回null
     */
    @Query("SELECT a.authId FROM AuthGlobalEntity a WHERE a.authKey=?1")
    Integer getAuthIdByAuthKey(String authKey);
}
