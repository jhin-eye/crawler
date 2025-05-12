package com.yanoos.global.entity.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1501395270L;

    public static final QMember member = new QMember("member1");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isApproved = createBoolean("isApproved");

    public final ListPath<Keyword, QKeyword> keywords = this.<Keyword, QKeyword>createList("keywords", Keyword.class, QKeyword.class, PathInits.DIRECT2);

    public final ListPath<MapMemberPost, QMapMemberPost> mapMemberPosts = this.<MapMemberPost, QMapMemberPost>createList("mapMemberPosts", MapMemberPost.class, QMapMemberPost.class, PathInits.DIRECT2);

    public final ListPath<MapMemberTelegramUser, QMapMemberTelegramUser> mapMemberTelegramUsers = this.<MapMemberTelegramUser, QMapMemberTelegramUser>createList("mapMemberTelegramUsers", MapMemberTelegramUser.class, QMapMemberTelegramUser.class, PathInits.DIRECT2);

    public final ListPath<MemberOAuth, QMemberOAuth> memberOAuths = this.<MemberOAuth, QMemberOAuth>createList("memberOAuths", MemberOAuth.class, QMemberOAuth.class, PathInits.DIRECT2);

    public final StringPath nickname = createString("nickname");

    public final StringPath role = createString("role");

    public final ComparablePath<java.util.UUID> telegramAuthenticationUuid = createComparable("telegramAuthenticationUuid", java.util.UUID.class);

    public final DateTimePath<java.time.LocalDateTime> telegramUuidCreatedAt = createDateTime("telegramUuidCreatedAt", java.time.LocalDateTime.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

