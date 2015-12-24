package lamp.server.aladin.admin.security;

import lamp.server.aladin.admin.domain.User;
import lamp.server.aladin.admin.security.exception.UserNotActivatedException;
import lamp.server.aladin.admin.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class AdminUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("Authenticating {}", username);
		String login = username.toLowerCase();
		Optional<User> userFromDatabase = userRepository.findOneByLogin(login);
		return userFromDatabase.map(user -> {
			if (!user.getActivated()) {
				throw new UserNotActivatedException("User " + login + " was not activated");
			}
			// AuthorityUtils
			List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
					.map(authority -> new SimpleGrantedAuthority(authority.getName()))
					.collect(Collectors.toList());
			return new org.springframework.security.core.userdetails.User(login,
					user.getPassword(),
					grantedAuthorities);
		}).orElseThrow(() -> new UsernameNotFoundException("User " + login + " was not found"));
	}

}
