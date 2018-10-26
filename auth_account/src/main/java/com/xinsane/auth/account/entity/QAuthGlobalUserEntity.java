package com.xinsane.auth.account.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthGlobalUserEntity is a Querydsl query type for AuthGlobalUserEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAuthGlobalUserEntity extends EntityPathBase<AuthGlobalUserEntity> {

    private static final long serialVersionUID = -960473930L;

    public static final QAuthGlobalUserEntity authGlobalUserEntity = new QAuthGlobalUserEntity("authGlobalUserEntity");

    public final NumberPath<Integer> authId = createNumber("authId", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QAuthGlobalUserEntity(String variable) {
        super(AuthGlobalUserEntity.class, forVariable(variable));
    }

    public QAuthGlobalUserEntity(Path<? extends AuthGlobalUserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthGlobalUserEntity(PathMetadata metadata) {
        super(AuthGlobalUserEntity.class, metadata);
    }

}

