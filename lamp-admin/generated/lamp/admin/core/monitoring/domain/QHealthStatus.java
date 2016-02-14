package lamp.admin.core.monitoring.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QHealthStatus is a Querydsl query type for HealthStatus
 */
@Generated("com.mysema.query.codegen.EmbeddableSerializer")
public class QHealthStatus extends BeanPath<HealthStatus> {

    private static final long serialVersionUID = 858363980L;

    public static final QHealthStatus healthStatus = new QHealthStatus("healthStatus");

    public final EnumPath<HealthStatusCode> code = createEnum("code", HealthStatusCode.class);

    public final StringPath description = createString("description");

    public QHealthStatus(String variable) {
        super(HealthStatus.class, forVariable(variable));
    }

    public QHealthStatus(Path<? extends HealthStatus> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHealthStatus(PathMetadata<?> metadata) {
        super(HealthStatus.class, metadata);
    }

}

