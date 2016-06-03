package lamp.admin.domain.script.repository;

import lamp.admin.domain.script.model.ScriptCommand;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScriptCommandRepository extends LampJpaRepository<ScriptCommand, Long> {


}
