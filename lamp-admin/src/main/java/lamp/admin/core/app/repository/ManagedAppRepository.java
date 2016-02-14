package lamp.admin.core.app.repository;

import lamp.admin.core.app.domain.ManagedApp;
import lamp.admin.core.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagedAppRepository extends LampJpaRepository<ManagedApp, String> {

}
