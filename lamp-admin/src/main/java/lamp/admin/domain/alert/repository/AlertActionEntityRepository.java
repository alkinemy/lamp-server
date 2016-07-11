package lamp.admin.domain.alert.repository;


import lamp.admin.domain.alert.model.entity.AlertActionEntity;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertActionEntityRepository extends LampJpaRepository<AlertActionEntity, String> {

	Page<AlertActionEntity> findAllByPrivatedFalse(Pageable pageable);

	List<AlertActionEntity> findAllByPrivatedFalse();

}
