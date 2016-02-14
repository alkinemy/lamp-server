package lamp.admin.web.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 86789894L;

    public static final QUser user = new QUser("user");

    public final lamp.admin.core.base.domain.QAbstractAuditingEntity _super = new lamp.admin.core.base.domain.QAbstractAuditingEntity(this);

    public final BooleanPath activated = createBoolean("activated");

    public final StringPath activationKey = createString("activationKey");

    public final SetPath<Authority, QAuthority> authorities = this.<Authority, QAuthority>createSet("authorities", Authority.class, QAuthority.class, PathInits.DIRECT2);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath email = createString("email");

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath langKey = createString("langKey");

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath lastName = createString("lastName");

    public final StringPath login = createString("login");

    public final StringPath password = createString("password");

    public final SetPath<PersistentToken, QPersistentToken> persistentTokens = this.<PersistentToken, QPersistentToken>createSet("persistentTokens", PersistentToken.class, QPersistentToken.class, PathInits.DIRECT2);

    public final DateTimePath<java.util.Date> resetDate = createDateTime("resetDate", java.util.Date.class);

    public final StringPath resetKey = createString("resetKey");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata<?> metadata) {
        super(User.class, metadata);
    }

}

