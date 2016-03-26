package lamp.admin.domain.app.repository;

import lamp.admin.domain.app.model.AppRepo;
import lamp.admin.domain.app.model.AppResourceType;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppRepoRepository extends LampJpaRepository<AppRepo, String> {

	List<AppRepo> findAllByRepositoryType(AppResourceType repositoryType);

	AppRepo findOneByName(String name);
}
