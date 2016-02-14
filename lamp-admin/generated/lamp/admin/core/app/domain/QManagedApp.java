package lamp.admin.core.app.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QManagedApp is a Querydsl query type for ManagedApp
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QManagedApp extends EntityPathBase<ManagedApp> {

    private static final long serialVersionUID = 1965076805L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QManagedApp managedApp = new QManagedApp("managedApp");

    public final lamp.admin.core.base.domain.QAbstractAuditingEntity _super = new lamp.admin.core.base.domain.QAbstractAuditingEntity(this);

    public final QAppTemplate appTemplate;

    public final StringPath artifactId = createString("artifactId");

    public final StringPath artifactName = createString("artifactName");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath description = createString("description");

    public final StringPath groupId = createString("groupId");

    public final StringPath id = createString("id");

    public final DateTimePath<java.time.LocalDateTime> installDate = createDateTime("installDate", java.time.LocalDateTime.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final DateTimePath<java.time.LocalDateTime> registerDate = createDateTime("registerDate", java.time.LocalDateTime.class);

    public final lamp.admin.core.agent.domain.QTargetServer targetServer;

    public final BooleanPath updatable = createBoolean("updatable");

    public final StringPath version = createString("version");

    public QManagedApp(String variable) {
        this(ManagedApp.class, forVariable(variable), INITS);
    }

    public QManagedApp(Path<? extends ManagedApp> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QManagedApp(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QManagedApp(PathMetadata<?> metadata, PathInits inits) {
        this(ManagedApp.class, metadata, inits);
    }

    public QManagedApp(Class<? extends ManagedApp> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.appTemplate = inits.isInitialized("appTemplate") ? new QAppTemplate(forProperty("appTemplate"), inits.get("appTemplate")) : null;
        this.targetServer = inits.isInitialized("targetServer") ? new lamp.admin.core.agent.domain.QTargetServer(forProperty("targetServer")) : null;
    }

}

