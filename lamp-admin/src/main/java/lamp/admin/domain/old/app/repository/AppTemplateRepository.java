package lamp.admin.domain.old.app.repository;

import lamp.admin.domain.old.app.model.AppTemplate;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppTemplateRepository extends LampJpaRepository<AppTemplate, String> {

}
