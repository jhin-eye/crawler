package com.yanoos.global.entity.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMapMemberTelegramUser is a Querydsl query type for MapMemberTelegramUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMapMemberTelegramUser extends EntityPathBase<MapMemberTelegramUser> {

    private static final long serialVersionUID = 1462315650L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMapMemberTelegramUser mapMemberTelegramUser = new QMapMemberTelegramUser("mapMemberTelegramUser");

    public final QMember member;

    public final NumberPath<Long> telegramUserId = createNumber("telegramUserId", Long.class);

    public QMapMemberTelegramUser(String variable) {
        this(MapMemberTelegramUser.class, forVariable(variable), INITS);
    }

    public QMapMemberTelegramUser(Path<? extends MapMemberTelegramUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMapMemberTelegramUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMapMemberTelegramUser(PathMetadata metadata, PathInits inits) {
        this(MapMemberTelegramUser.class, metadata, inits);
    }

    public QMapMemberTelegramUser(Class<? extends MapMemberTelegramUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

