package lamp.admin.core.agent.repository;

import lamp.admin.core.agent.domain.TargetServerStatus;
import lamp.admin.core.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetServerStatusRepository extends LampJpaRepository<TargetServerStatus, Long> {

}
