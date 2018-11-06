package com.xinsane.auth.account.repository;

import com.xinsane.auth.account.entity.AuthGlobalEntity;
import com.xinsane.auth.account.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    /**
     * 检查用户是否有某全局权限
     * @param userId 用户id
     * @param authKey 权限key
     * @return 若有指定权限则返回true，否则返回false
     */
    @Query("SELECT (COUNT(au.id) > 0) FROM AuthGlobalUserEntity au " +
            "INNER JOIN AuthGlobalEntity a ON a.authKey=?2 AND a.authId=au.authId " +
            "WHERE au.userId=?1")
    boolean hasAuth(int userId, String authKey);

    /**
     * 查询用户拥有的所有全局权限
     * @param userId 用户id
     * @return 用户拥有的全局权限列表
     */
    @Query("FROM AuthGlobalEntity a INNER JOIN AuthGlobalUserEntity au ON au.userId=?1 AND au.authId=a.authId")
    List<AuthGlobalEntity> getUserAuth(int userId);

    /**
     * 查询用户拥有的所有全局权限id
     * @param userId 用户id
     * @return 用户拥有的全局权限id列表
     */
    @Query("SELECT a.authId FROM AuthGlobalEntity a " +
            "INNER JOIN AuthGlobalUserEntity au ON au.userId=?1 AND au.authId=a.authId")
    List<Integer> getUserAuthId(int userId);

    UserEntity findFirstByUsername(String username);
    UserEntity findFirstByEmail(String email);
    UserEntity findFirstByPhone(String phone);
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);
}
