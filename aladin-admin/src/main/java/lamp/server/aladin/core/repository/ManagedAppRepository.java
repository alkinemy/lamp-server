package lamp.server.aladin.core.repository;

import lamp.server.aladin.core.domain.ManagedApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagedAppRepository extends JpaRepository<ManagedApp, String> {

}
