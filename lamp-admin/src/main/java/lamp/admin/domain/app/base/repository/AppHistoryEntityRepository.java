package lamp.admin.domain.app.base.repository;

import lamp.admin.domain.app.base.model.entity.AppHistoryEntity;
import lamp.admin.domain.app.base.model.entity.AppHistoryId;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AppHistoryEntityRepository extends LampJpaRepository<AppHistoryEntity, AppHistoryId> {


}
