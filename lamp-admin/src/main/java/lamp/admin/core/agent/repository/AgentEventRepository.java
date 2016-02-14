package lamp.admin.core.agent.repository;

import lamp.admin.core.agent.domain.AgentEvent;
import lamp.admin.core.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentEventRepository extends LampJpaRepository<AgentEvent, Long> {


}
