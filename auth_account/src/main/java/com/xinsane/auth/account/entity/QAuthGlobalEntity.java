package com.xinsane.auth.account.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthGlobalEntity is a Querydsl query type for AuthGlobalEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAuthGlobalEntity extends EntityPathBase<AuthGlobalEntity> {

    private static final long serialVersionUID = -247687925L;

    public static final QAuthGlobalEntity authGlobalEntity = new QAuthGlobalEntity("authGlobalEntity");

    public final StringPath authChildren = createString("authChildren");

    public final StringPath authDescription = createString("authDescription");

    public final NumberPath<Integer> authId = createNumber("authId", Integer.class);

    public final StringPath authKey = createString("authKey");

    public final StringPath authName = createString("authName");

    public final StringPath authParent = createString("authParent");

    public QAuthGlobalEntity(String variable) {
        super(AuthGlobalEntity.class, forVariable(variable));
    }

    public QAuthGlobalEntity(Path<? extends AuthGlobalEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthGlobalEntity(PathMetadata metadata) {
        super(AuthGlobalEntity.class, metadata);
    }

}

