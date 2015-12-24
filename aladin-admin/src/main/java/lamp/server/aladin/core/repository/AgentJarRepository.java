package lamp.server.aladin.core.repository;

import lamp.server.aladin.core.domain.AgentJar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentJarRepository extends JpaRepository<AgentJar, Long> {

}
