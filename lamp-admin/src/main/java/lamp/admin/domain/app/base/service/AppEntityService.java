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

	public List<AppEntity> getListByParentPath(String parentPath) {
		return appEntityRepository.findAllByParentPath(parentPath);
	}

	public AppEntity getByPath(String path) {
		return appEntityRepository.findOneByPath(path);
	}

	public AppEntity createAppEntity(AppEntity entity) {
		return appEntityRepository.save(entity);
	}

	public void deleteAppEntity(String id) {
		appEntityRepository.delete(id);
	}
}
