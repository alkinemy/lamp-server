package lamp.admin.core.agent.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAgentEvent is a Querydsl query type for AgentEvent
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QAgentEvent extends EntityPathBase<AgentEvent> {

    private static final long serialVersionUID = -1328967620L;

    public static final QAgentEvent agentEvent = new QAgentEvent("agentEvent");

    public final lamp.admin.core.base.domain.QAbstractAuditingEntity _super = new lamp.admin.core.base.domain.QAbstractAuditingEntity(this);

    public final StringPath agentId = createString("agentId");

    public final NumberPath<Long> agentInstanceEventSequence = createNumber("agentInstanceEventSequence", Long.class);

    public final NumberPath<Long> agentInstanceId = createNumber("agentInstanceId", Long.class);

    public final StringPath appId = createString("appId");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath eventLevel = createString("eventLevel");

    public final StringPath eventName = createString("eventName");

    public final DateTimePath<java.time.LocalDateTime> eventTime = createDateTime("eventTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath message = createString("message");

    public QAgentEvent(String variable) {
        super(AgentEvent.class, forVariable(variable));
    }

    public QAgentEvent(Path<? extends AgentEvent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAgentEvent(PathMetadata<?> metadata) {
        super(AgentEvent.class, metadata);
    }

}

