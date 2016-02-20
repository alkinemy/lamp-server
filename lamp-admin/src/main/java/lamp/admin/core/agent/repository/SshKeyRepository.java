package lamp.admin.core.agent.repository;

import lamp.admin.core.agent.domain.SshKey;
import lamp.admin.core.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SshKeyRepository extends LampJpaRepository<SshKey, Long> {

}
