package lamp.admin.web.account.repository;

import lamp.admin.domain.base.repository.LampJpaRepository;
import lamp.admin.web.account.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends LampJpaRepository<User, Long> {

	Optional<User> findOneByLogin(String login);
	Optional<User> findOneByEmail(String email);

	List<User> findAllByActivatedIsFalse();

}
