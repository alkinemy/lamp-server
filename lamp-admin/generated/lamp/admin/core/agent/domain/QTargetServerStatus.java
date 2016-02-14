package lamp.admin.core.agent.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QTargetServerStatus is a Querydsl query type for TargetServerStatus
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QTargetServerStatus extends EntityPathBase<TargetServerStatus> {

    private static final long serialVersionUID = 1636351981L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTargetServerStatus targetServerStatus = new QTargetServerStatus("targetServerStatus");

    public final lamp.admin.core.monitoring.domain.QHealthStatus agentStatus;

    public final DateTimePath<java.time.LocalDateTime> agentStatusDate = createDateTime("agentStatusDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QTargetServerStatus(String variable) {
        this(TargetServerStatus.class, forVariable(variable), INITS);
    }

    public QTargetServerStatus(Path<? extends TargetServerStatus> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QTargetServerStatus(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QTargetServerStatus(PathMetadata<?> metadata, PathInits inits) {
        this(TargetServerStatus.class, metadata, inits);
    }

    public QTargetServerStatus(Class<? extends TargetServerStatus> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.agentStatus = inits.isInitialized("agentStatus") ? new lamp.admin.core.monitoring.domain.QHealthStatus(forProperty("agentStatus")) : null;
    }

}

