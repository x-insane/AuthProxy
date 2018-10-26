package com.xinsane.auth.account.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthPageEntity is a Querydsl query type for AuthPageEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAuthPageEntity extends EntityPathBase<AuthPageEntity> {

    private static final long serialVersionUID = 1565505591L;

    public static final QAuthPageEntity authPageEntity = new QAuthPageEntity("authPageEntity");

    public final NumberPath<Integer> authType = createNumber("authType", Integer.class);

    public final NumberPath<Integer> pageId = createNumber("pageId", Integer.class);

    public final StringPath pagePath = createString("pagePath");

    public final NumberPath<Integer> siteId = createNumber("siteId", Integer.class);

    public QAuthPageEntity(String variable) {
        super(AuthPageEntity.class, forVariable(variable));
    }

    public QAuthPageEntity(Path<? extends AuthPageEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthPageEntity(PathMetadata metadata) {
        super(AuthPageEntity.class, metadata);
    }

}

