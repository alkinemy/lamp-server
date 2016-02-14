package lamp.admin.core.agent.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QAgent is a Querydsl query type for Agent
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QAgent extends EntityPathBase<Agent> {

    private static final long serialVersionUID = -759897858L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAgent agent = new QAgent("agent");

    public final lamp.admin.core.base.domain.QAbstractAuditingEntity _super = new lamp.admin.core.base.domain.QAbstractAuditingEntity(this);

    public final StringPath address = createString("address");

    public final StringPath appDirectory = createString("appDirectory");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath healthPath = createString("healthPath");

    public final StringPath hostname = createString("hostname");

    public final StringPath id = createString("id");

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath metricsPath = createString("metricsPath");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> port = createNumber("port", Integer.class);

    public final StringPath protocol = createString("protocol");

    public final StringPath secretKey = createString("secretKey");

    public final QTargetServer targetServer;

    public final DateTimePath<java.time.LocalDateTime> time = createDateTime("time", java.time.LocalDateTime.class);

    public final StringPath type = createString("type");

    public final StringPath version = createString("version");

    public QAgent(String variable) {
        this(Agent.class, forVariable(variable), INITS);
    }

    public QAgent(Path<? extends Agent> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAgent(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAgent(PathMetadata<?> metadata, PathInits inits) {
        this(Agent.class, metadata, inits);
    }

    public QAgent(Class<? extends Agent> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.targetServer = inits.isInitialized("targetServer") ? new QTargetServer(forProperty("targetServer")) : null;
    }

}

