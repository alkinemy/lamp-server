package lamp.admin.domain.host.repository;

import lamp.admin.core.host.ClusterType;
import lamp.admin.domain.base.repository.LampJpaRepository;
import lamp.admin.domain.host.model.entity.ClusterEntity;
import lamp.admin.domain.host.model.entity.HostEntity;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClusterEntityRepository extends LampJpaRepository<ClusterEntity, String> {

	List<ClusterEntity> findAllByType(ClusterType type);
}
