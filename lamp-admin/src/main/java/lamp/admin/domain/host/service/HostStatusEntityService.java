package lamp.admin.domain.host.service;


import lamp.admin.domain.host.model.entity.HostStatusEntity;
import lamp.admin.domain.host.repository.HostStatusEntityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HostStatusEntityService {
	
	@Autowired
	private HostStatusEntityRepository hostStatusEntityRepository;

	@Transactional
	public void update(HostStatusEntity hostStatusEntity) {
		HostStatusEntity saved = hostStatusEntityRepository.findOne(hostStatusEntity.getId());
		BeanUtils.copyProperties(hostStatusEntity, saved, "id");
	}

}



