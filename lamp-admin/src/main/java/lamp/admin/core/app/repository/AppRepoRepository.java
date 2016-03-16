package lamp.admin.core.app.repository;

import lamp.admin.core.app.domain.AppRepo;
import lamp.admin.core.app.domain.AppResourceType;
import lamp.admin.core.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppRepoRepository extends LampJpaRepository<AppRepo, String> {

	List<AppRepo> findAllByRepositoryType(AppResourceType repositoryType);

	AppRepo findOneByName(String name);
}
