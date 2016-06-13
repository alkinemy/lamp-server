package lamp.admin.domain.script.repository;

import lamp.admin.domain.script.model.ScriptCommandEntity;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScriptCommandEntityRepository extends LampJpaRepository<ScriptCommandEntity, Long> {


}
