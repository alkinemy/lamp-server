package lamp.admin.core.app.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLocalAppFile is a Querydsl query type for LocalAppFile
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QLocalAppFile extends EntityPathBase<LocalAppFile> {

    private static final long serialVersionUID = -77293611L;

    public static final QLocalAppFile localAppFile = new QLocalAppFile("localAppFile");

    public final lamp.admin.core.base.domain.QAbstractAuditingEntity _super = new lamp.admin.core.base.domain.QAbstractAuditingEntity(this);

    public final StringPath artifactId = createString("artifactId");

    public final StringPath baseVersion = createString("baseVersion");

    public final StringPath contentType = createString("contentType");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath description = createString("description");

    public final DateTimePath<java.time.LocalDateTime> fileDate = createDateTime("fileDate", java.time.LocalDateTime.class);

    public final StringPath filename = createString("filename");

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    public final StringPath groupId = createString("groupId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath pathname = createString("pathname");

    public final NumberPath<Long> repositoryId = createNumber("repositoryId", Long.class);

    public final StringPath version = createString("version");

    public QLocalAppFile(String variable) {
        super(LocalAppFile.class, forVariable(variable));
    }

    public QLocalAppFile(Path<? extends LocalAppFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocalAppFile(PathMetadata<?> metadata) {
        super(LocalAppFile.class, metadata);
    }

}

