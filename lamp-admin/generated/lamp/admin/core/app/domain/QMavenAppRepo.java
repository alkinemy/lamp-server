package lamp.admin.core.app.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QMavenAppRepo is a Querydsl query type for MavenAppRepo
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QMavenAppRepo extends EntityPathBase<MavenAppRepo> {

    private static final long serialVersionUID = 551638955L;

    public static final QMavenAppRepo mavenAppRepo = new QMavenAppRepo("mavenAppRepo");

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

    public final StringPath password = createString("password");

    public final StringPath proxy = createString("proxy");

    //inherited
    public final EnumPath<AppResourceType> repositoryType = _super.repositoryType;

    public final StringPath url = createString("url");

    public final StringPath username = createString("username");

    public QMavenAppRepo(String variable) {
        super(MavenAppRepo.class, forVariable(variable));
    }

    public QMavenAppRepo(Path<? extends MavenAppRepo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMavenAppRepo(PathMetadata<?> metadata) {
        super(MavenAppRepo.class, metadata);
    }

}

