package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.ManagedApp;
import lamp.server.aladin.core.repository.ManagedAppRepository;
import lamp.server.aladin.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ManagedAppService {

	@Autowired
	private SmartAssembler smartAssembler;
	@Autowired
	private ManagedAppRepository managedAppRepository;

	@Transactional
	public ManagedApp insert(ManagedApp managedApp) {
		return managedAppRepository.save(managedApp);
	}

	@Transactional
	public void delete(String id) {
		managedAppRepository.delete(id);
	}

}
