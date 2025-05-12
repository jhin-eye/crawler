package com.yanoos.global.entity.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -517642518L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final StringPath className = createString("className");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.ZonedDateTime> lastCrawledAt = createDateTime("lastCrawledAt", java.time.ZonedDateTime.class);

    public final StringPath nameEng = createString("nameEng");

    public final StringPath nameKor = createString("nameKor");

    public final NumberPath<Integer> pageSize = createNumber("pageSize", Integer.class);

    public final DateTimePath<java.time.ZonedDateTime> previousCrawledAt = createDateTime("previousCrawledAt", java.time.ZonedDateTime.class);

    public final StringPath siteUrl = createString("siteUrl");

    public final QBoardType type;

    public final StringPath url = createString("url");

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.type = inits.isInitialized("type") ? new QBoardType(forProperty("type")) : null;
    }

}

