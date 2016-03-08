package lamp.collector.common.repository;

import lamp.collector.common.domain.AppEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppEventRepository extends JpaRepository<AppEvent, Long> {


}
