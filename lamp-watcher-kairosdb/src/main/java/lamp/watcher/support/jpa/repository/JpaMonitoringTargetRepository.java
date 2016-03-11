package lamp.watcher.support.jpa.repository;

import lamp.watcher.support.jpa.domain.JpaMonitoringTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMonitoringTargetRepository extends JpaRepository<JpaMonitoringTarget, String> {


}
