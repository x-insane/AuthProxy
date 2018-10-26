package com.xinsane.auth.account.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QLoginAuthTokenEntity is a Querydsl query type for LoginAuthTokenEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLoginAuthTokenEntity extends EntityPathBase<LoginAuthTokenEntity> {

    private static final long serialVersionUID = 769827112L;

    public static final QLoginAuthTokenEntity loginAuthTokenEntity = new QLoginAuthTokenEntity("loginAuthTokenEntity");

    public final DateTimePath<java.sql.Timestamp> expireTime = createDateTime("expireTime", java.sql.Timestamp.class);

    public final StringPath tokenCode = createString("tokenCode");

    public final NumberPath<Integer> tokenId = createNumber("tokenId", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QLoginAuthTokenEntity(String variable) {
        super(LoginAuthTokenEntity.class, forVariable(variable));
    }

    public QLoginAuthTokenEntity(Path<? extends LoginAuthTokenEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLoginAuthTokenEntity(PathMetadata metadata) {
        super(LoginAuthTokenEntity.class, metadata);
    }

}

