package lamp.admin.domain.app.base.repository;

import lamp.admin.domain.app.base.model.entity.AppEntity;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppEntityRepository extends LampJpaRepository<AppEntity, String> {

}
