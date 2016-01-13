package lamp.server.aladin.core.repository;

import lamp.server.aladin.core.domain.AppFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppFileRepository extends JpaRepository<AppFile, Long> {

}
