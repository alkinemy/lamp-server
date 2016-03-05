package lamp.collector.core.repository;

import lamp.collector.core.domain.WatchedApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchedAppRepository extends JpaRepository<WatchedApp, String> {


}
