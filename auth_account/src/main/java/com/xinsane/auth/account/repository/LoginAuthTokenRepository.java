package com.xinsane.auth.account.repository;

import com.xinsane.auth.account.entity.LoginAuthTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface LoginAuthTokenRepository extends CrudRepository<LoginAuthTokenEntity, Integer> {
}
