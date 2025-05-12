package com.yanoos.global.entity.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMapMemberPost is a Querydsl query type for MapMemberPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMapMemberPost extends EntityPathBase<MapMemberPost> {

    private static final long serialVersionUID = 1002002614L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMapMemberPost mapMemberPost = new QMapMemberPost("mapMemberPost");

    public final BooleanPath checked = createBoolean("checked");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<String, StringPath> keywords = this.<String, StringPath>createList("keywords", String.class, StringPath.class, PathInits.DIRECT2);

    public final QMember member;

    public final com.yanoos.global.entity.board.QPost post;

    public QMapMemberPost(String variable) {
        this(MapMemberPost.class, forVariable(variable), INITS);
    }

    public QMapMemberPost(Path<? extends MapMemberPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMapMemberPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMapMemberPost(PathMetadata metadata, PathInits inits) {
        this(MapMemberPost.class, metadata, inits);
    }

    public QMapMemberPost(Class<? extends MapMemberPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.post = inits.isInitialized("post") ? new com.yanoos.global.entity.board.QPost(forProperty("post"), inits.get("post")) : null;
    }

}

