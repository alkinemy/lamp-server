package lamp.admin.domain.app.base.service;

import lamp.admin.domain.app.base.model.entity.AppInstanceEntity;
import lamp.admin.domain.app.base.repository.AppInstanceEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppInstanceEntityService {

	@Autowired
	private AppInstanceEntityRepository appInstanceEntityRepository;

	public List<AppInstanceEntity> getListByAppId(String appId) {
		return appInstanceEntityRepository.findAllByAppId(appId);
	}

	public AppInstanceEntity get(String id) {
		return appInstanceEntityRepository.findOne(id);
	}

	public AppInstanceEntity create(AppInstanceEntity appInstanceEntity) {
		return appInstanceEntityRepository.save(appInstanceEntity);
	}

	public void delete(String id) {
		appInstanceEntityRepository.delete(id);
	}
}
