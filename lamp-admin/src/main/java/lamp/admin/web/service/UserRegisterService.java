package lamp.admin.web.service;

import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.utils.assembler.SmartAssembler;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.domain.User;
import lamp.admin.web.domain.UserRegisterForm;
import lamp.admin.web.repository.UserRepository;
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
		User user = assembler.assemble(form, User.class);
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		return userRepository.save(user);
	}
}
