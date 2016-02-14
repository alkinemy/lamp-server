package lamp.admin.core.app.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QLocalAppRepo is a Querydsl query type for LocalAppRepo
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QLocalAppRepo extends EntityPathBase<LocalAppRepo> {

    private static final long serialVersionUID = -76939829L;

    public static final QLocalAppRepo localAppRepo = new QLocalAppRepo("localAppRepo");

    public final QAppRepo _super = new QAppRepo(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final StringPath description = _super.description;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    //inherited
    public final StringPath name = _super.name;

    public final StringPath repositoryPath = createString("repositoryPath");

    //inherited
    public final EnumPath<AppResourceType> repositoryType = _super.repositoryType;

    public QLocalAppRepo(String variable) {
        super(LocalAppRepo.class, forVariable(variable));
    }

    public QLocalAppRepo(Path<? extends LocalAppRepo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocalAppRepo(PathMetadata<?> metadata) {
        super(LocalAppRepo.class, metadata);
    }

}

