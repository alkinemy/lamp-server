package lamp.admin.domain.app.repo.service.param;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LocalAppFileSearchParams {

	private String repositoryId;

	public Predicate buildPredicate() {
//		QLocalAppFile qLocalAppFile = QLocalAppFile.localAppFile;
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		if (repositoryId != null) {
//			booleanBuilder.and(qLocalAppFile.repositoryId.eq(repositoryId));
		}
		return booleanBuilder;
	}
}
