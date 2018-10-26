package com.xinsane.auth.account.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthTokenEntity is a Querydsl query type for AuthTokenEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAuthTokenEntity extends EntityPathBase<AuthTokenEntity> {

    private static final long serialVersionUID = 2145983223L;

    public static final QAuthTokenEntity authTokenEntity = new QAuthTokenEntity("authTokenEntity");

    public final DateTimePath<java.sql.Timestamp> createTime = createDateTime("createTime", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> expireTime = createDateTime("expireTime", java.sql.Timestamp.class);

    public final NumberPath<Integer> tokenId = createNumber("tokenId", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QAuthTokenEntity(String variable) {
        super(AuthTokenEntity.class, forVariable(variable));
    }

    public QAuthTokenEntity(Path<? extends AuthTokenEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthTokenEntity(PathMetadata metadata) {
        super(AuthTokenEntity.class, metadata);
    }

}

