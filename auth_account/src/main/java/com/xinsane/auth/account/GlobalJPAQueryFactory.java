package com.xinsane.auth.account;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class GlobalJPAQueryFactory extends JPAQueryFactory {
    @Autowired
    public GlobalJPAQueryFactory(EntityManager entityManager) {
        super(entityManager);
    }
}
