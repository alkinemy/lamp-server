package lamp.collector.core.repository;

import lamp.collector.core.domain.TargetApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetAppRepository extends JpaRepository<TargetApp, String> {


}
