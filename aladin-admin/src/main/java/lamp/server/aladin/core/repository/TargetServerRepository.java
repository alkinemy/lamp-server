package lamp.server.aladin.core.repository;

import lamp.server.aladin.core.domain.TargetServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetServerRepository extends JpaRepository<TargetServer, Long> {

}
