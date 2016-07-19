package lamp.admin.domain.agent.repository;

import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepository extends LampJpaRepository<Agent, String> {

	Optional<Agent> findOneByAddress(String address);

}
