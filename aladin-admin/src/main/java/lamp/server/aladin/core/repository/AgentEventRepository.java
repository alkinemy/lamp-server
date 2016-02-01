package lamp.server.aladin.core.repository;

import lamp.server.aladin.core.domain.AgentEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentEventRepository extends JpaRepository<AgentEvent, Long> {


}
