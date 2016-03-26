package lamp.admin.domain.app.service;

import lamp.admin.domain.app.model.ManagedApp;
import lamp.admin.domain.app.repository.ManagedAppRepository;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
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

	@Transactional
	public void delete(ManagedApp managedApp) {
		managedAppRepository.delete(managedApp);
	}

}
