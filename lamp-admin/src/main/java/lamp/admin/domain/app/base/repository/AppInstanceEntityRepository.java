package lamp.admin.domain.app.base.repository;

import lamp.admin.domain.app.base.model.entity.AppInstanceEntity;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppInstanceEntityRepository extends LampJpaRepository<AppInstanceEntity, String> {

	List<AppInstanceEntity> findAllByAppId(String appId);
}
