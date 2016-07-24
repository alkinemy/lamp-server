package lamp.admin.domain.host.service;


import lamp.admin.core.host.Host;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.admin.domain.host.model.entity.HostEntity;
import lamp.admin.domain.host.repository.HostEntityRepository;
import lamp.admin.web.AdminErrorCode;
import lamp.common.utils.StringUtils;
import lamp.common.utils.assembler.SmartAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostService {
	
	@Autowired
	private HostEntityRepository hostEntityRepository;

	@Autowired
	private SmartAssembler smartAssembler;

	public List<Host> getHostList() {
		return smartAssembler.assemble(getHostEntityList(), HostEntity.class, Host.class);
	}

	public List<Host> getHostListByClusterId(String clusterId) {
		if (StringUtils.isBlank(clusterId)) {
			return getHostList();
		}
		return smartAssembler.assemble(getHostEntityList(clusterId), HostEntity.class, Host.class);
	}

	public List<HostEntity> getHostEntityList() {
		return hostEntityRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
	}

	public List<HostEntity> getHostEntityList(String clusterId) {
		return hostEntityRepository.findAllByClusterId(new Sort(Sort.Direction.ASC, "name"));
	}


	public Host getHost(String id) {
		Optional<Host> hostOptional = getHostOptional(id);
		Exceptions.throwsException(!hostOptional.isPresent(), AdminErrorCode.HOST_NOT_FOUND, id);
		return hostOptional.get();
	}

	public Optional<Host> getHostOptional(String id) {
		Optional<HostEntity> hostEntityOptional = getHostEntityOptional(id);
		Host host =  smartAssembler.assemble(hostEntityOptional.orElse(null), HostEntity.class, Host.class);
		return Optional.ofNullable(host);
	}

	public HostEntity getHostEntity(String id) {
		Optional<HostEntity> hostEntityOptional = getHostEntityOptional(id);
		Exceptions.throwsException(!hostEntityOptional.isPresent(), LampErrorCode.HOST_NOT_FOUND);
		return hostEntityOptional.get();
	}

	public Optional<HostEntity> getHostEntityOptional(String id) {
		return Optional.ofNullable(hostEntityRepository.findOne(id));
	}

	public Optional<HostEntity> getHostEntityOptionalByAddress(String address) {
		return hostEntityRepository.findOneByAddress(address);
	}


	public Host addHost(Host host) {
		HostEntity hostEntity = smartAssembler.assemble(host, Host.class, HostEntity.class);
		HostEntity saved = addHostEntity(hostEntity);
		return smartAssembler.assemble(saved, HostEntity.class, Host.class);
	}

	public HostEntity addHostEntity(HostEntity hostEntity) {
		return hostEntityRepository.save(hostEntity);
	}

	public Host updateHost(Host host) {
		HostEntity hostEntity = getHostEntity(host.getId());
		smartAssembler.populate(host, hostEntity);
		return smartAssembler.assemble(hostEntity, HostEntity.class, Host.class);
	}

	public void removeHostEntity(String id) {
		hostEntityRepository.delete(id);
	}


}



