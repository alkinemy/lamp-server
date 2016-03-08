package lamp.collector.app.support.jpa.repository;

import lamp.collector.app.support.jpa.domain.JpaEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEventRepository extends JpaRepository<JpaEvent, Long> {


}
