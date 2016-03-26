package lamp.admin.domain.app.repository;

import lamp.admin.domain.app.model.AppInstallScript;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppInstallScriptRepository extends LampJpaRepository<AppInstallScript, Long> {

	List<AppInstallScript> findAllByTemplateId(String templateId);

	Page<AppInstallScript> findAllByTemplateId(String templateId, Pageable pageable);
}
