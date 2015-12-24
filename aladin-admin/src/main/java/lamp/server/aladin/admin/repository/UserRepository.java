package lamp.server.aladin.admin.repository;

import lamp.server.aladin.admin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findOneByLogin(String login);

}
