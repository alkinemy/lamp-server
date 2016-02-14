package lamp.admin.core.app.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QAppTemplate is a Querydsl query type for AppTemplate
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QAppTemplate extends EntityPathBase<AppTemplate> {

    private static final long serialVersionUID = 188418776L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAppTemplate appTemplate = new QAppTemplate("appTemplate");

    public final lamp.admin.core.base.domain.QAbstractAuditingEntity _super = new lamp.admin.core.base.domain.QAbstractAuditingEntity(this);

    public final StringPath appDirectory = createString("appDirectory");

    public final StringPath appFilename = createString("appFilename");

    public final QAppRepo appRepository;

    public final StringPath artifactId = createString("artifactId");

    public final StringPath artifactName = createString("artifactName");

    public final StringPath commands = createString("commands");

    public final StringPath commandShell = createString("commandShell");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath description = createString("description");

    public final StringPath errorLogFile = createString("errorLogFile");

    public final StringPath groupId = createString("groupId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath logFile = createString("logFile");

    public final BooleanPath monitor = createBoolean("monitor");

    public final StringPath name = createString("name");

    public final StringPath pidFile = createString("pidFile");

    public final BooleanPath preInstalled = createBoolean("preInstalled");

    public final EnumPath<AppProcessType> processType = createEnum("processType", AppProcessType.class);

    public final EnumPath<AppResourceType> resourceType = createEnum("resourceType", AppResourceType.class);

    public final StringPath resourceUrl = createString("resourceUrl");

    public final StringPath startCommandLine = createString("startCommandLine");

    public final StringPath stopCommandLine = createString("stopCommandLine");

    public final StringPath version = createString("version");

    public final StringPath workDirectory = createString("workDirectory");

    public QAppTemplate(String variable) {
        this(AppTemplate.class, forVariable(variable), INITS);
    }

    public QAppTemplate(Path<? extends AppTemplate> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAppTemplate(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAppTemplate(PathMetadata<?> metadata, PathInits inits) {
        this(AppTemplate.class, metadata, inits);
    }

    public QAppTemplate(Class<? extends AppTemplate> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.appRepository = inits.isInitialized("appRepository") ? new QAppRepo(forProperty("appRepository")) : null;
    }

}

