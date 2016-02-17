package lamp.admin.core.app.repository;

import lamp.admin.core.app.domain.AppInstallScript;
import lamp.admin.core.base.repository.LampJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppInstallScriptRepository extends LampJpaRepository<AppInstallScript, Long> {

	List<AppInstallScript> findAllByTemplateId(Long templateId);

	Page<AppInstallScript> findAllByTemplateId(Long templateId, Pageable pageable);
}
