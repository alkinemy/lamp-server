package lamp.admin.domain.resource.repo.repository;



import lamp.admin.core.app.simple.resource.AppResourceType;
import lamp.admin.domain.resource.repo.model.entity.AppRepoEntity;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppRepoEntityRepository extends LampJpaRepository<AppRepoEntity, String> {

	List<AppRepoEntity> findAllByRepositoryType(AppResourceType repositoryType);

	AppRepoEntity findOneByName(String name);
}
