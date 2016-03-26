package lamp.admin.domain.agent.repository;

import lamp.admin.domain.agent.model.SshKey;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SshKeyRepository extends LampJpaRepository<SshKey, Long> {

}
