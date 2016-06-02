package lamp.admin.domain.app.repo.repository;



import lamp.admin.domain.app.repo.model.AppResourceType;
import lamp.admin.domain.app.repo.model.entity.AppRepoEntity;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppRepoEntityRepository extends LampJpaRepository<AppRepoEntity, String> {

	List<AppRepoEntity> findAllByRepositoryType(AppResourceType repositoryType);

	AppRepoEntity findOneByName(String name);
}
