package lamp.server.aladin.core.repository;

import lamp.server.aladin.core.domain.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<Agent, String> {

	Optional<Agent> findOneByHostname(String hostname);

	Agent findOneByTargetSErverId(Long id);
}
