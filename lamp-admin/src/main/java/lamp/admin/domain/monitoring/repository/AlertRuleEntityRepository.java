package lamp.admin.domain.monitoring.repository;

import lamp.admin.domain.base.repository.LampJpaRepository;
import lamp.admin.domain.monitoring.model.AlertRuleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRuleEntityRepository extends LampJpaRepository<AlertRuleEntity, String> {

	List<AlertRuleEntity> findAllByType(String type);
}
