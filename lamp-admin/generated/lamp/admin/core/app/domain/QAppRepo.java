package lamp.admin.core.app.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QAppRepo is a Querydsl query type for AppRepo
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QAppRepo extends EntityPathBase<AppRepo> {

    private static final long serialVersionUID = 1633646800L;

    public static final QAppRepo appRepo = new QAppRepo("appRepo");

    public final lamp.admin.core.base.domain.QAbstractAuditingEntity _super = new lamp.admin.core.base.domain.QAbstractAuditingEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final EnumPath<AppResourceType> repositoryType = createEnum("repositoryType", AppResourceType.class);

    public QAppRepo(String variable) {
        super(AppRepo.class, forVariable(variable));
    }

    public QAppRepo(Path<? extends AppRepo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAppRepo(PathMetadata<?> metadata) {
        super(AppRepo.class, metadata);
    }

}

