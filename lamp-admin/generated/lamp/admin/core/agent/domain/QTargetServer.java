package lamp.admin.core.agent.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QTargetServer is a Querydsl query type for TargetServer
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QTargetServer extends EntityPathBase<TargetServer> {

    private static final long serialVersionUID = 2008073243L;

    public static final QTargetServer targetServer = new QTargetServer("targetServer");

    public final lamp.admin.core.base.domain.QAbstractAuditingEntity _super = new lamp.admin.core.base.domain.QAbstractAuditingEntity(this);

    public final StringPath address = createString("address");

    public final StringPath agentHealthUrl = createString("agentHealthUrl");

    public final BooleanPath agentInstalled = createBoolean("agentInstalled");

    public final StringPath agentInstalledBy = createString("agentInstalledBy");

    public final DateTimePath<java.time.LocalDateTime> agentInstalledDate = createDateTime("agentInstalledDate", java.time.LocalDateTime.class);

    public final StringPath agentInstallFilename = createString("agentInstallFilename");

    public final StringPath agentInstallPath = createString("agentInstallPath");

    public final BooleanPath agentMonitor = createBoolean("agentMonitor");

    public final NumberPath<Long> agentMonitorInterval = createNumber("agentMonitorInterval", Long.class);

    public final StringPath agentPidFile = createString("agentPidFile");

    public final StringPath agentStartCommandLine = createString("agentStartCommandLine");

    public final StringPath agentStatus = createString("agentStatus");

    public final DateTimePath<java.time.LocalDateTime> agentStatusDate = createDateTime("agentStatusDate", java.time.LocalDateTime.class);

    public final StringPath agentStopCommandLine = createString("agentStopCommandLine");

    public final EnumPath<SshAuthType> authType = createEnum("authType", SshAuthType.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath description = createString("description");

    public final StringPath hostname = createString("hostname");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath privateKey = createString("privateKey");

    public final NumberPath<Integer> sshPort = createNumber("sshPort", Integer.class);

    public final StringPath username = createString("username");

    public QTargetServer(String variable) {
        super(TargetServer.class, forVariable(variable));
    }

    public QTargetServer(Path<? extends TargetServer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTargetServer(PathMetadata<?> metadata) {
        super(TargetServer.class, metadata);
    }

}

