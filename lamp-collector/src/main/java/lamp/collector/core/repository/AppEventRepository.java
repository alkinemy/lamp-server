package lamp.collector.core.repository;

import lamp.collector.core.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppEventRepository extends JpaRepository<Event, Long> {


}
