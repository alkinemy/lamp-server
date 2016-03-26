package lamp.admin.domain.app.repository;

import lamp.admin.domain.app.model.ManagedApp;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagedAppRepository extends LampJpaRepository<ManagedApp, String> {

}
