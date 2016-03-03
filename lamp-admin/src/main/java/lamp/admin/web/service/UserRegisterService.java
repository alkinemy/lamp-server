package lamp.admin.web.service;

import lamp.admin.utils.assembler.SmartAssembler;
import lamp.admin.web.domain.User;
import lamp.admin.web.domain.UserRegisterForm;
import lamp.admin.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegisterService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SmartAssembler assembler;

	@Transactional
	public User regiseter(UserRegisterForm form) {
//		User user = assembler.assemble(form, User.class);
//		return userRepository.save(user);
		return null;
	}
}
