package lamp.admin.web.account.repository;

import lamp.admin.domain.base.repository.LampJpaRepository;
import lamp.admin.web.account.model.PersistentToken;
import lamp.admin.web.account.model.User;

import java.util.Date;
import java.util.List;


public interface PersistentTokenRepository extends LampJpaRepository<PersistentToken, String> {

    List<PersistentToken> findByUser(User user);

    List<PersistentToken> findByTokenDateBefore(Date localDate);

}
