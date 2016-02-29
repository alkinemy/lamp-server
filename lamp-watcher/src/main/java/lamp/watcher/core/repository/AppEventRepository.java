package lamp.watcher.core.repository;

import lamp.watcher.core.domain.AppEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppEventRepository extends JpaRepository<AppEvent, Long> {


}
