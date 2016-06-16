package lamp.admin.domain.host.repository;

import lamp.admin.domain.base.repository.LampJpaRepository;
import lamp.admin.domain.host.model.entity.HostStatusEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface HostStatusEntityRepository extends LampJpaRepository<HostStatusEntity, String> {

}
