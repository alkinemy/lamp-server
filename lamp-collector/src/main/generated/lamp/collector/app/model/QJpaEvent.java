package lamp.collector.app.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QJpaEvent is a Querydsl query type for JpaEvent
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QJpaEvent extends EntityPathBase<JpaEvent> {

    private static final long serialVersionUID = -199873656L;

    public static final QJpaEvent jpaEvent = new QJpaEvent("jpaEvent");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<lamp.common.event.EventLevel> level = createEnum("level", lamp.common.event.EventLevel.class);

    public final StringPath message = createString("message");

    public final StringPath name = createString("name");

    public final StringPath sourceHostname = createString("sourceHostname");

    public final StringPath sourceId = createString("sourceId");

    public final StringPath sourceName = createString("sourceName");

    public final DateTimePath<java.time.LocalDateTime> time = createDateTime("time", java.time.LocalDateTime.class);

    public QJpaEvent(String variable) {
        super(JpaEvent.class, forVariable(variable));
    }

    public QJpaEvent(Path<? extends JpaEvent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJpaEvent(PathMetadata<?> metadata) {
        super(JpaEvent.class, metadata);
    }

}

