package lamp.admin.domain.host.service;


import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.admin.domain.host.model.entity.HostEntity;
import lamp.admin.domain.host.repository.HostEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostEntityService {
	
	@Autowired
	private HostEntityRepository hostEntityRepository;

	public List<HostEntity> getList() {
		return hostEntityRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
	}

	public List<HostEntity> getList(String clusterId) {
		return hostEntityRepository.findAllByClusterId(new Sort(Sort.Direction.ASC, "name"));
	}

	public HostEntity get(String id) {
		HostEntity hostEntity = hostEntityRepository.findOne(id);
		Exceptions.throwsException(hostEntity == null, LampErrorCode.HOST_NOT_FOUND);
		return hostEntity;
	}

	public HostEntity create(HostEntity hostEntity) {
		return hostEntityRepository.save(hostEntity);
	}

	public Optional<HostEntity> getOptionalByAddress(String address) {
		return hostEntityRepository.findOneByAddress(address);
	}

	public void delete(String id) {
		hostEntityRepository.delete(id);
	}
}



