package com.xinsane.auth.account.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserInvitationEntity is a Querydsl query type for UserInvitationEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserInvitationEntity extends EntityPathBase<UserInvitationEntity> {

    private static final long serialVersionUID = 1097713188L;

    public static final QUserInvitationEntity userInvitationEntity = new QUserInvitationEntity("userInvitationEntity");

    public final StringPath authGlobal = createString("authGlobal");

    public final StringPath authPage = createString("authPage");

    public final StringPath authSite = createString("authSite");

    public final NumberPath<Byte> bindChild = createNumber("bindChild", Byte.class);

    public final DateTimePath<java.sql.Timestamp> createTime = createDateTime("createTime", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> expireTime = createDateTime("expireTime", java.sql.Timestamp.class);

    public final StringPath inviteCode = createString("inviteCode");

    public final NumberPath<Integer> inviteId = createNumber("inviteId", Integer.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QUserInvitationEntity(String variable) {
        super(UserInvitationEntity.class, forVariable(variable));
    }

    public QUserInvitationEntity(Path<? extends UserInvitationEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserInvitationEntity(PathMetadata metadata) {
        super(UserInvitationEntity.class, metadata);
    }

}

