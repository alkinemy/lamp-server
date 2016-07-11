package lamp.admin.domain.alert.repository;


import lamp.admin.domain.alert.model.entity.AlertRuleEntity;
import lamp.admin.domain.base.repository.LampJpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRuleEntityRepository extends LampJpaRepository<AlertRuleEntity, String> {

	List<AlertRuleEntity> findAllByDataType(String dataType);

	Page<AlertRuleEntity> findAllByDataType(String dataType, Pageable pageable);
}
