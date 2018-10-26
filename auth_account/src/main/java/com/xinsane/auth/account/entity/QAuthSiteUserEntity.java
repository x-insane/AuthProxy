package com.xinsane.auth.account.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthSiteUserEntity is a Querydsl query type for AuthSiteUserEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAuthSiteUserEntity extends EntityPathBase<AuthSiteUserEntity> {

    private static final long serialVersionUID = 1588394138L;

    public static final QAuthSiteUserEntity authSiteUserEntity = new QAuthSiteUserEntity("authSiteUserEntity");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Byte> manageable = createNumber("manageable", Byte.class);

    public final NumberPath<Byte> shareable = createNumber("shareable", Byte.class);

    public final NumberPath<Integer> siteId = createNumber("siteId", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QAuthSiteUserEntity(String variable) {
        super(AuthSiteUserEntity.class, forVariable(variable));
    }

    public QAuthSiteUserEntity(Path<? extends AuthSiteUserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthSiteUserEntity(PathMetadata metadata) {
        super(AuthSiteUserEntity.class, metadata);
    }

}

