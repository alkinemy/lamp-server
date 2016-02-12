package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.ManagedApp;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.core.exception.LampErrorCode;
import lamp.server.aladin.core.repository.ManagedAppRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class ManagedAppService {

	@Autowired
	private ManagedAppRepository managedAppRepository;

	public Page<ManagedApp> getManagedAppList(Pageable pageable) {
		return managedAppRepository.findAll(pageable);
	}


	public ManagedApp getManagedApp(String id) {
		Optional<ManagedApp> managedAppOptional = getManagedAppOptional(id);
		return managedAppOptional.orElseThrow(() -> Exceptions.newException(LampErrorCode.MANAGED_APP_NOT_FOUND, id));
	}

	public Optional<ManagedApp> getManagedAppOptional(String id) {
		return Optional.ofNullable(managedAppRepository.findOne(id));
	}

	@Transactional
	public ManagedApp insert(ManagedApp managedApp) {
		return managedAppRepository.save(managedApp);
	}

	@Transactional
	public void delete(String id) {
		managedAppRepository.delete(id);
	}

}
