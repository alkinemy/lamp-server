package lamp.server.aladin.domain.client.repository;

import lamp.server.aladin.domain.client.model.LampClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LampClientRepository extends JpaRepository<LampClient, String> {

}
