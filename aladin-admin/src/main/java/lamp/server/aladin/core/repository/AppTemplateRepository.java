package lamp.server.aladin.core.repository;

import lamp.server.aladin.core.domain.AppTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppTemplateRepository extends JpaRepository<AppTemplate, Long> {

}
