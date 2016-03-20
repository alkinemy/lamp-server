package lamp.collector.app.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QCollectionTarget is a Querydsl query type for CollectionTarget
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QCollectionTarget extends EntityPathBase<CollectionTarget> {

    private static final long serialVersionUID = 1331763416L;

    public static final QCollectionTarget collectionTarget = new QCollectionTarget("collectionTarget");

    public final StringPath address = createString("address");

    public final StringPath artifactId = createString("artifactId");

    public final StringPath groupId = createString("groupId");

    public final BooleanPath healthCollectionEnabled = createBoolean("healthCollectionEnabled");

    public final StringPath healthExportPrefix = createString("healthExportPrefix");

    public final StringPath healthType = createString("healthType");

    public final StringPath healthUrl = createString("healthUrl");

    public final StringPath hostname = createString("hostname");

    public final StringPath id = createString("id");

    public final BooleanPath metricsCollectionEnabled = createBoolean("metricsCollectionEnabled");

    public final StringPath metricsExportPrefix = createString("metricsExportPrefix");

    public final StringPath metricsType = createString("metricsType");

    public final StringPath metricsUrl = createString("metricsUrl");

    public final StringPath name = createString("name");

    public final StringPath version = createString("version");

    public QCollectionTarget(String variable) {
        super(CollectionTarget.class, forVariable(variable));
    }

    public QCollectionTarget(Path<? extends CollectionTarget> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCollectionTarget(PathMetadata<?> metadata) {
        super(CollectionTarget.class, metadata);
    }

}

