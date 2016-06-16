package lamp.admin.domain.monitoring.repository;

import lamp.admin.domain.base.repository.LampJpaRepository;
import lamp.admin.domain.monitoring.model.WatchTarget;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchTargetRepository extends LampJpaRepository<WatchTarget, String> {

}
