package lamp.admin.domain.app.base.service;

import lamp.admin.domain.app.base.model.entity.AppInstanceEntity;
import lamp.admin.domain.app.base.repository.AppInstanceEntityRepository;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppInstanceEntityService {

	@Autowired
	private AppInstanceEntityRepository appInstanceEntityRepository;

	public List<AppInstanceEntity> getListByAppId(String appId) {
		return appInstanceEntityRepository.findAllByAppId(appId);
	}

	public AppInstanceEntity get(String id) {
		Optional<AppInstanceEntity> optional = getOptional(id);
		Exceptions.throwsException(!optional.isPresent(), LampErrorCode.APP_INSTANCE_NOT_FOUND, id);
		return optional.get();
	}

	public Optional<AppInstanceEntity> getOptional(String id) {
		return Optional.ofNullable(appInstanceEntityRepository.findOne(id));
	}

	public AppInstanceEntity create(AppInstanceEntity appInstanceEntity) {
		return appInstanceEntityRepository.save(appInstanceEntity);
	}

	public void delete(String id) {
		appInstanceEntityRepository.delete(id);
	}
}
