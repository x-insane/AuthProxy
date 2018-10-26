package com.xinsane.auth.account.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthTokenPageEntity is a Querydsl query type for AuthTokenPageEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAuthTokenPageEntity extends EntityPathBase<AuthTokenPageEntity> {

    private static final long serialVersionUID = 1819433062L;

    public static final QAuthTokenPageEntity authTokenPageEntity = new QAuthTokenPageEntity("authTokenPageEntity");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> pageId = createNumber("pageId", Integer.class);

    public final NumberPath<Integer> tokenId = createNumber("tokenId", Integer.class);

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public final NumberPath<Integer> viewCountLimit = createNumber("viewCountLimit", Integer.class);

    public QAuthTokenPageEntity(String variable) {
        super(AuthTokenPageEntity.class, forVariable(variable));
    }

    public QAuthTokenPageEntity(Path<? extends AuthTokenPageEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthTokenPageEntity(PathMetadata metadata) {
        super(AuthTokenPageEntity.class, metadata);
    }

}

