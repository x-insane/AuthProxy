package com.xinsane.auth.account.repository;

import com.xinsane.auth.account.entity.ConfigEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConfigRepository extends CrudRepository<ConfigEntity, Integer> {
    ConfigEntity queryByKey(String key);

    @Query("FROM ConfigEntity")
    List<ConfigEntity> queryAll();

    /**
     * 根据配置key查询值value
     * @param key 配置key
     * @return 对应配置的值，若无则返回null
     */
    @Query("SELECT c.value FROM ConfigEntity c WHERE c.key=?1")
    String value(String key);
}
