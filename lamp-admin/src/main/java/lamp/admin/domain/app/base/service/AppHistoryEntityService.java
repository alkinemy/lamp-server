package lamp.admin.domain.app.base.service;

import lamp.admin.domain.app.base.model.entity.AppHistoryEntity;
import lamp.admin.domain.app.base.repository.AppHistoryEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AppHistoryEntityService {

	@Autowired
	private AppHistoryEntityRepository appHistoryEntityRepository;

	@Transactional
	public AppHistoryEntity create(AppHistoryEntity entity) {
		log.error("AppHistoryEntity = {}", entity);
		return appHistoryEntityRepository.save(entity);
	}

}
