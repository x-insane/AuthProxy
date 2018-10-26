package com.xinsane.auth.account.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthPageUserEntity is a Querydsl query type for AuthPageUserEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAuthPageUserEntity extends EntityPathBase<AuthPageUserEntity> {

    private static final long serialVersionUID = -520748574L;

    public static final QAuthPageUserEntity authPageUserEntity = new QAuthPageUserEntity("authPageUserEntity");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> pageId = createNumber("pageId", Integer.class);

    public final NumberPath<Byte> shareable = createNumber("shareable", Byte.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public final NumberPath<Integer> viewCountLimit = createNumber("viewCountLimit", Integer.class);

    public QAuthPageUserEntity(String variable) {
        super(AuthPageUserEntity.class, forVariable(variable));
    }

    public QAuthPageUserEntity(Path<? extends AuthPageUserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthPageUserEntity(PathMetadata metadata) {
        super(AuthPageUserEntity.class, metadata);
    }

}

