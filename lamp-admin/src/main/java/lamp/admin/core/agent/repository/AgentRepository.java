package lamp.admin.core.agent.repository;

import lamp.admin.core.agent.domain.Agent;
import lamp.admin.core.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepository extends LampJpaRepository<Agent, String> {

	Optional<Agent> findOneByHostname(String hostname);

	Agent findOneByTargetServerId(Long id);
}
