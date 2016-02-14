package lamp.admin.web.repository;

import lamp.admin.core.base.repository.LampJpaRepository;
import lamp.admin.web.domain.PersistentToken;
import lamp.admin.web.domain.User;

import java.util.Date;
import java.util.List;


public interface PersistentTokenRepository extends LampJpaRepository<PersistentToken, String> {

    List<PersistentToken> findByUser(User user);

    List<PersistentToken> findByTokenDateBefore(Date localDate);

}
