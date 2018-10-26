package com.xinsane.auth.account.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthTokenSiteEntity is a Querydsl query type for AuthTokenSiteEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAuthTokenSiteEntity extends EntityPathBase<AuthTokenSiteEntity> {

    private static final long serialVersionUID = 836856606L;

    public static final QAuthTokenSiteEntity authTokenSiteEntity = new QAuthTokenSiteEntity("authTokenSiteEntity");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> siteId = createNumber("siteId", Integer.class);

    public final NumberPath<Integer> tokenId = createNumber("tokenId", Integer.class);

    public QAuthTokenSiteEntity(String variable) {
        super(AuthTokenSiteEntity.class, forVariable(variable));
    }

    public QAuthTokenSiteEntity(Path<? extends AuthTokenSiteEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthTokenSiteEntity(PathMetadata metadata) {
        super(AuthTokenSiteEntity.class, metadata);
    }

}

