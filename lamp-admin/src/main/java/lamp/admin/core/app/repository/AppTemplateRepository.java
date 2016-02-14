package lamp.admin.core.app.repository;

import lamp.admin.core.app.domain.AppTemplate;
import lamp.admin.core.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppTemplateRepository extends LampJpaRepository<AppTemplate, Long> {

}
