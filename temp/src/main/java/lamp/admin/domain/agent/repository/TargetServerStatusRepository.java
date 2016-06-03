package lamp.admin.domain.agent.repository;

import lamp.admin.domain.agent.model.TargetServerStatus;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetServerStatusRepository extends LampJpaRepository<TargetServerStatus, String> {

}
