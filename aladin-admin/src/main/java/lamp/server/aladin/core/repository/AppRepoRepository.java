package lamp.server.aladin.core.repository;

import lamp.server.aladin.core.domain.AppResourceType;
import lamp.server.aladin.core.domain.AppRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppRepoRepository extends JpaRepository<AppRepo, Long> {

	List<AppRepo> findAllByRepositoryType(AppResourceType repositoryType);

	AppRepo findOneByName(String name);
}
