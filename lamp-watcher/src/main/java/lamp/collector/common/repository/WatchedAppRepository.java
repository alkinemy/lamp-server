package lamp.collector.common.repository;

import lamp.collector.common.domain.MonitoringTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchedAppRepository extends JpaRepository<MonitoringTarget, String> {


}
