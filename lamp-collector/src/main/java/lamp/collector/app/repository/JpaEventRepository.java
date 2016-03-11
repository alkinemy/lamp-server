package lamp.collector.app.repository;

import lamp.collector.app.domain.JpaEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEventRepository extends JpaRepository<JpaEvent, Long> {


}
