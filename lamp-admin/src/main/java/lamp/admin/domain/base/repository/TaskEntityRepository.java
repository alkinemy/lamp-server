package lamp.admin.domain.base.repository;

import lamp.admin.domain.base.model.TaskStatus;
import lamp.admin.domain.base.model.entity.TaskEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskEntityRepository extends LampJpaRepository<TaskEntity, String> {

	List<TaskEntity> findAllByDataTypeAndStatusIn(String dataType, TaskStatus[] status);
}
