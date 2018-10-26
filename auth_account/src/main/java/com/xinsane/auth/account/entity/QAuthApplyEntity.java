package com.xinsane.auth.account.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthApplyEntity is a Querydsl query type for AuthApplyEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAuthApplyEntity extends EntityPathBase<AuthApplyEntity> {

    private static final long serialVersionUID = 1771845804L;

    public static final QAuthApplyEntity authApplyEntity = new QAuthApplyEntity("authApplyEntity");

    public final NumberPath<Integer> applyId = createNumber("applyId", Integer.class);

    public final DateTimePath<java.sql.Timestamp> createTime = createDateTime("createTime", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> handleTime = createDateTime("handleTime", java.sql.Timestamp.class);

    public final NumberPath<Integer> handleUserId = createNumber("handleUserId", Integer.class);

    public final StringPath notifyEmail = createString("notifyEmail");

    public final StringPath notifyPhone = createString("notifyPhone");

    public final NumberPath<Integer> pageId = createNumber("pageId", Integer.class);

    public final NumberPath<Integer> siteId = createNumber("siteId", Integer.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QAuthApplyEntity(String variable) {
        super(AuthApplyEntity.class, forVariable(variable));
    }

    public QAuthApplyEntity(Path<? extends AuthApplyEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthApplyEntity(PathMetadata metadata) {
        super(AuthApplyEntity.class, metadata);
    }

}

