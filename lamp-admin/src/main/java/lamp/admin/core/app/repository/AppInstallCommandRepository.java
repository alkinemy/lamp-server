package lamp.admin.core.app.repository;

import lamp.admin.core.app.domain.AppInstallCommand;
import lamp.admin.core.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppInstallCommandRepository extends LampJpaRepository<AppInstallCommand, Long> {


}
