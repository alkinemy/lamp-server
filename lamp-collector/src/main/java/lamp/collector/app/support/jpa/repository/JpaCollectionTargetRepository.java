package lamp.collector.app.support.jpa.repository;

import lamp.collector.app.support.jpa.domain.JpaCollectionTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCollectionTargetRepository extends JpaRepository<JpaCollectionTarget, String> {


}
