package lamp.server.aladin.core.repository;

import lamp.server.aladin.core.domain.TargetServerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetServerStatusRepository extends JpaRepository<TargetServerStatus, Long> {

}
