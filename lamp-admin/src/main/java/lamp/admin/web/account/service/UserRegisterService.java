package lamp.admin.web.account.service;

import com.google.common.collect.Sets;
import lamp.admin.core.base.exception.Exceptions;
import lamp.common.utils.assembler.SmartAssembler;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.account.model.Authority;
import lamp.admin.web.account.model.User;
import lamp.admin.web.account.model.UserRegisterForm;
import lamp.admin.web.account.repository.UserRepository;
import lamp.admin.web.support.security.AuthoritiesConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserRegisterService {
	//TODO UserDetailsManager 적용?

	@Autowired
	private SmartAssembler assembler;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public User register(UserRegisterForm form) {
		userRepository.findOneByLogin(form.getLogin()).ifPresent(user -> {
			throw Exceptions.newException(AdminErrorCode.USER_DUPLICATED_LOGIN);
		});
		userRepository.findOneByEmail(form.getEmail()).ifPresent(user -> {
			throw Exceptions.newException(AdminErrorCode.USER_DUPLICATED_EMAIL);
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

	@Transactional(readOnly = true)
	public List<User> getNotActivatedUsers() {
		return userRepository.findAllByActivatedIsFalse();
	}

	@Transactional
	public void allow(Long id) {
		User user = Optional.of(userRepository.findOne(id)).orElseThrow(() -> Exceptions.newException(AdminErrorCode.USER_NOT_EXIST));
		user.setActivated(true);
	}
}
