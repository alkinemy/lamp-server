package lamp.admin.domain.app.repository;

import lamp.admin.domain.app.model.AppTemplate;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppTemplateRepository extends LampJpaRepository<AppTemplate, String> {

}
