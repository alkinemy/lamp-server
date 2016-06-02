package lamp.admin.domain.old.app.repository;

import lamp.admin.domain.old.app.model.ManagedApp;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagedAppRepository extends LampJpaRepository<ManagedApp, String> {

}
