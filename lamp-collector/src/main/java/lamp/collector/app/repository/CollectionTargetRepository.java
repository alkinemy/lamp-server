package lamp.collector.app.repository;

import lamp.collector.app.model.CollectionTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionTargetRepository extends JpaRepository<CollectionTarget, String> {


}
