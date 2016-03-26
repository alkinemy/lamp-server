package lamp.admin.domain.agent.repository;

import lamp.admin.domain.agent.model.AgentEvent;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentEventRepository extends LampJpaRepository<AgentEvent, Long> {


}
