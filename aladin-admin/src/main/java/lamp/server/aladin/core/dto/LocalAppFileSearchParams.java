package lamp.server.aladin.core.dto;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import lamp.server.aladin.core.domain.QLocalAppFile;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LocalAppFileSearchParams {

	private Long repositoryId;

	public Predicate buildPredicate() {
		QLocalAppFile qLocalAppFile = QLocalAppFile.localAppFile;
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		if (repositoryId != null) {
			booleanBuilder.and(qLocalAppFile.repositoryId.eq(repositoryId));
		}
		return booleanBuilder;
	}
}
