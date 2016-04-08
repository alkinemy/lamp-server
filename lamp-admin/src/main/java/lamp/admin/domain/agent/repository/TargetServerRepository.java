package lamp.admin.domain.agent.repository;

import lamp.admin.domain.agent.model.TargetServer;
import lamp.admin.domain.agent.model.TargetServerDto;
import lamp.admin.domain.base.repository.LampJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TargetServerRepository extends LampJpaRepository<TargetServer, String> {

	Optional<TargetServer> findOneByHostname(String hostname);

	List<TargetServerDto> findAllByIdIn(List<String> ids);
}
