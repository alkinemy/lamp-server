package lamp.collector.core.repository;

import lamp.collector.core.domain.CollectionTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionTargetRepository extends JpaRepository<CollectionTarget, String> {


}
