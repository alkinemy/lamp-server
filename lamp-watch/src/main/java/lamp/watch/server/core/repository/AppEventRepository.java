package lamp.watch.server.core.repository;

import lamp.watch.server.core.domain.AppEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppEventRepository extends JpaRepository<AppEvent, Long> {


}
