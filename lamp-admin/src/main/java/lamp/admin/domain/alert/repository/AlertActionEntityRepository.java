package lamp.admin.domain.alert.repository;


import lamp.admin.domain.alert.model.entity.AlertActionEntity;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertActionEntityRepository extends LampJpaRepository<AlertActionEntity, String> {

}
