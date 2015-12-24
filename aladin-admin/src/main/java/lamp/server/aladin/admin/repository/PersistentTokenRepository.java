package lamp.server.aladin.admin.repository;

import lamp.server.aladin.admin.domain.PersistentToken;
import lamp.server.aladin.admin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;


public interface PersistentTokenRepository extends JpaRepository<PersistentToken, String> {

    List<PersistentToken> findByUser(User user);

    List<PersistentToken> findByTokenDateBefore(Date localDate);

}
