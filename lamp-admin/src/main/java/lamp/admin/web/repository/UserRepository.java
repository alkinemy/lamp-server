package lamp.admin.web.repository;

import lamp.admin.core.base.repository.LampJpaRepository;
import lamp.admin.web.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends LampJpaRepository<User, Long> {

	Optional<User> findOneByLogin(String login);

}
