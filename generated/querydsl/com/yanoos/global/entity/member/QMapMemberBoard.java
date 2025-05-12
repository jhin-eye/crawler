package com.yanoos.global.entity.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMapMemberBoard is a Querydsl query type for MapMemberBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMapMemberBoard extends EntityPathBase<MapMemberBoard> {

    private static final long serialVersionUID = 984363408L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMapMemberBoard mapMemberBoard = new QMapMemberBoard("mapMemberBoard");

    public final com.yanoos.global.entity.board.QBoard board;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public QMapMemberBoard(String variable) {
        this(MapMemberBoard.class, forVariable(variable), INITS);
    }

    public QMapMemberBoard(Path<? extends MapMemberBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMapMemberBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMapMemberBoard(PathMetadata metadata, PathInits inits) {
        this(MapMemberBoard.class, metadata, inits);
    }

    public QMapMemberBoard(Class<? extends MapMemberBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new com.yanoos.global.entity.board.QBoard(forProperty("board"), inits.get("board")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

