package lamp.admin.domain.host.repository;

import lamp.admin.domain.base.repository.LampJpaRepository;
import lamp.admin.domain.host.model.entity.SshKeyEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface SshKeyEntityRepository extends LampJpaRepository<SshKeyEntity, String> {


}
