package lamp.server.watch.core.repository;

import lamp.server.watch.core.domain.AppEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppEventRepository extends JpaRepository<AppEvent, Long> {


}
