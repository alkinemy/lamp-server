package lamp.admin.core.script.repository;

import lamp.admin.core.script.domain.ScriptCommand;
import lamp.admin.core.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScriptCommandRepository extends LampJpaRepository<ScriptCommand, Long> {


}
