package com.xinsane.auth.account.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthSiteEntity is a Querydsl query type for AuthSiteEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAuthSiteEntity extends EntityPathBase<AuthSiteEntity> {

    private static final long serialVersionUID = 582929135L;

    public static final QAuthSiteEntity authSiteEntity = new QAuthSiteEntity("authSiteEntity");

    public final StringPath siteDescription = createString("siteDescription");

    public final NumberPath<Integer> siteId = createNumber("siteId", Integer.class);

    public final StringPath siteName = createString("siteName");

    public final StringPath siteUrl = createString("siteUrl");

    public QAuthSiteEntity(String variable) {
        super(AuthSiteEntity.class, forVariable(variable));
    }

    public QAuthSiteEntity(Path<? extends AuthSiteEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthSiteEntity(PathMetadata metadata) {
        super(AuthSiteEntity.class, metadata);
    }

}

