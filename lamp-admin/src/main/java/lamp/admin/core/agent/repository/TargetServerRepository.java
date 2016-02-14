package lamp.admin.core.agent.repository;

import lamp.admin.core.agent.domain.TargetServer;
import lamp.admin.core.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TargetServerRepository extends LampJpaRepository<TargetServer, Long> {

	Optional<TargetServer> findOneByHostname(String hostname);
}
