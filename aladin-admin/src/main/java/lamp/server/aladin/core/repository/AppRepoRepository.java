package lamp.server.aladin.core.repository;

import lamp.server.aladin.core.domain.AppRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepoRepository extends JpaRepository<AppRepo, Long> {

}
