package com.xinsane.auth.account.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QConfigEntity is a Querydsl query type for ConfigEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QConfigEntity extends EntityPathBase<ConfigEntity> {

    private static final long serialVersionUID = 2028610082L;

    public static final QConfigEntity configEntity = new QConfigEntity("configEntity");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath key = createString("key");

    public final StringPath value = createString("value");

    public QConfigEntity(String variable) {
        super(ConfigEntity.class, forVariable(variable));
    }

    public QConfigEntity(Path<? extends ConfigEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConfigEntity(PathMetadata metadata) {
        super(ConfigEntity.class, metadata);
    }

}

