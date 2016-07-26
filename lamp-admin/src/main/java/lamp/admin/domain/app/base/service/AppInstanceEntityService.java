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

	public List<AppInstanceEntity> getList() {
		return appInstanceEntityRepository.findAll();
	}

	public List<AppInstanceEntity> getListByAppId(String appId) {
		return appInstanceEntityRepository.findAllByAppId(appId);
	}

	public List<AppInstanceEntity> getListByHostId(String hostId) {
		return appInstanceEntityRepository.findAllByHostId(hostId);
	}

	public AppInstanceEntity getAppInstanceEntity(String id) {
		Optional<AppInstanceEntity> optional = getOptionalAppInstanceEntity(id);
		Exceptions.throwsException(!optional.isPresent(), LampErrorCode.APP_INSTANCE_NOT_FOUND, id);
		return optional.get();
	}

	public Optional<AppInstanceEntity> getOptionalAppInstanceEntity(String id) {
		return Optional.ofNullable(appInstanceEntityRepository.findOne(id));
	}

	public AppInstanceEntity addAppInstanceEntity(AppInstanceEntity appInstanceEntity) {
		return appInstanceEntityRepository.save(appInstanceEntity);
	}

	public void deleteAppInstanceEntity(String id) {
		appInstanceEntityRepository.delete(id);
	}

}
