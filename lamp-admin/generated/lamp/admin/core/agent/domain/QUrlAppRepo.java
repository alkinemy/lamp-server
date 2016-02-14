package lamp.admin.core.agent.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QUrlAppRepo is a Querydsl query type for UrlAppRepo
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QUrlAppRepo extends EntityPathBase<UrlAppRepo> {

    private static final long serialVersionUID = -1415500181L;

    public static final QUrlAppRepo urlAppRepo = new QUrlAppRepo("urlAppRepo");

    public final lamp.admin.core.app.domain.QAppRepo _super = new lamp.admin.core.app.domain.QAppRepo(this);

    public final StringPath authType = createString("authType");

    public final StringPath authUrl = createString("authUrl");

    public final StringPath baseUrl = createString("baseUrl");

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

    //inherited
    public final EnumPath<lamp.admin.core.app.domain.AppResourceType> repositoryType = _super.repositoryType;

    public final StringPath username = createString("username");

    public QUrlAppRepo(String variable) {
        super(UrlAppRepo.class, forVariable(variable));
    }

    public QUrlAppRepo(Path<? extends UrlAppRepo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUrlAppRepo(PathMetadata<?> metadata) {
        super(UrlAppRepo.class, metadata);
    }

}

