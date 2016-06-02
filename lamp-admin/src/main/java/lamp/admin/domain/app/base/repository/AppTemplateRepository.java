package lamp.admin.domain.app.base.repository;

import lamp.admin.domain.base.repository.LampJpaRepository;
import lamp.admin.domain.old.app.model.AppTemplate;
import org.springframework.stereotype.Repository;

@Repository
public interface AppTemplateRepository extends LampJpaRepository<AppTemplate, String> {

}
