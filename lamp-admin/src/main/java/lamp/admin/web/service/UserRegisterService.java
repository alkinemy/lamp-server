package lamp.admin.web.service;

import com.google.common.collect.Sets;
import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.utils.assembler.SmartAssembler;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.domain.Authority;
import lamp.admin.web.domain.User;
import lamp.admin.web.domain.UserRegisterForm;
import lamp.admin.web.repository.UserRepository;
import lamp.admin.web.security.AuthoritiesConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegisterService {

	@Autowired
	private SmartAssembler assembler;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public User register(UserRegisterForm form) {
		userRepository.findOneByLogin(form.getLogin()).ifPresent(user -> {
			throw Exceptions.newException(AdminErrorCode.DUPLICATED_USER_LOGIN);
		});
		userRepository.findOneByEmail(form.getEmail()).ifPresent(user -> {
			throw Exceptions.newException(AdminErrorCode.DUPLICATED_USER_EMAIL);
		});

		Authority userAuthority = new Authority();
		userAuthority.setName(AuthoritiesConstants.USER);

		User user = assembler.assemble(form, User.class);
		user.setFirstName(form.getUsername());
		user.setLastName(form.getUsername());
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		user.setAuthorities(Sets.newHashSet(userAuthority));
		return userRepository.save(user);
	}
}
