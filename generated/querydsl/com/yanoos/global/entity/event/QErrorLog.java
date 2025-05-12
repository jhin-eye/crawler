package com.yanoos.global.entity.event;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QErrorLog is a Querydsl query type for ErrorLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QErrorLog extends EntityPathBase<ErrorLog> {

    private static final long serialVersionUID = -1380811676L;

    public static final QErrorLog errorLog = new QErrorLog("errorLog");

    public final BooleanPath checked = createBoolean("checked");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath log = createString("log");

    public final DateTimePath<java.time.ZonedDateTime> occurrenceTime = createDateTime("occurrenceTime", java.time.ZonedDateTime.class);

    public final StringPath topic = createString("topic");

    public final StringPath topic2 = createString("topic2");

    public QErrorLog(String variable) {
        super(ErrorLog.class, forVariable(variable));
    }

    public QErrorLog(Path<? extends ErrorLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QErrorLog(PathMetadata metadata) {
        super(ErrorLog.class, metadata);
    }

}

