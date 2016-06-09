package lamp.admin.domain.app.base.service;

import lamp.admin.domain.app.base.model.entity.AppEntity;
import lamp.admin.domain.app.base.repository.AppEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppEntityService {

	@Autowired
	private AppEntityRepository appEntityRepository;

	public List<AppEntity> getAppEntityListByPath(String path) {
		return appEntityRepository.findAllByPath(path);
	}

	public AppEntity getAppEntity(String id) {
		return appEntityRepository.findOne(id);
	}

	public AppEntity addAppEntity(AppEntity entity) {
		return appEntityRepository.save(entity);
	}


}
