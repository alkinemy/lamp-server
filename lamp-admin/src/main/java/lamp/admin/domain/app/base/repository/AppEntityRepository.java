package lamp.admin.domain.app.base.repository;

import lamp.admin.domain.app.base.model.entity.AppEntity;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppEntityRepository extends LampJpaRepository<AppEntity, String> {

	List<AppEntity> findAllByPath(String path);

}
