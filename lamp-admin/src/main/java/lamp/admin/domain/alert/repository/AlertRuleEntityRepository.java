package lamp.admin.domain.alert.repository;


import lamp.admin.domain.alert.model.entity.AlertRuleEntity;
import lamp.admin.domain.base.repository.LampJpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRuleEntityRepository extends LampJpaRepository<AlertRuleEntity, String> {

	List<AlertRuleEntity> findAllByType(String type);

}
