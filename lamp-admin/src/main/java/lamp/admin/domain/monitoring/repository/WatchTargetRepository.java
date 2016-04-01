package lamp.admin.domain.monitoring.repository;

import lamp.admin.domain.agent.model.TargetServer;
import lamp.admin.domain.base.repository.LampJpaRepository;
import lamp.admin.domain.monitoring.model.WatchTarget;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WatchTargetRepository extends LampJpaRepository<WatchTarget, String> {

}
