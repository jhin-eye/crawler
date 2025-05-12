package com.yanoos.global.entity.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -1124659172L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final QBoard board;

    public final StringPath boardNameEng = createString("boardNameEng");

    public final StringPath content = createString("content");

    public final StringPath department = createString("department");

    public final StringPath endpoint = createString("endpoint");

    public final StringPath headerParameters = createString("headerParameters");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.yanoos.global.entity.member.MapMemberPost, com.yanoos.global.entity.member.QMapMemberPost> mapMemberPost = this.<com.yanoos.global.entity.member.MapMemberPost, com.yanoos.global.entity.member.QMapMemberPost>createList("mapMemberPost", com.yanoos.global.entity.member.MapMemberPost.class, com.yanoos.global.entity.member.QMapMemberPost.class, PathInits.DIRECT2);

    public final StringPath method = createString("method");

    public final DateTimePath<java.time.ZonedDateTime> monitorTime = createDateTime("monitorTime", java.time.ZonedDateTime.class);

    public final StringPath no = createString("no");

    public final StringPath parameters = createString("parameters");

    public final StringPath title = createString("title");

    public final StringPath url = createString("url");

    public final DateTimePath<java.time.ZonedDateTime> writeDate = createDateTime("writeDate", java.time.ZonedDateTime.class);

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
    }

}

