package lamp.admin.domain.docker.service;

import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.docker.model.DockerApp;
import lamp.admin.domain.docker.model.DockerAppEntity;
import lamp.admin.domain.docker.repository.DockerAppEntityRepository;
import lamp.admin.web.AdminErrorCode;
import lamp.common.utils.assembler.SmartAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DockerAppService {

	@Autowired
	private SmartAssembler smartAssembler;
	@Autowired
	private DockerAppEntityRepository dockerAppEntityRepository;


	public List<DockerApp> getDockerApps() {
		List<DockerAppEntity> dockerAppEntities = dockerAppEntityRepository.findAll();
		return smartAssembler.assemble(dockerAppEntities, DockerApp.class);
	}

	public DockerApp createDockerApp(DockerApp dockerApp) {
		DockerAppEntity entity = smartAssembler.assemble(dockerApp, DockerAppEntity.class);
		DockerAppEntity saved = dockerAppEntityRepository.save(entity);
		return smartAssembler.assemble(saved, DockerApp.class);
	}

	public DockerApp getDockerApp(String id) {
		DockerAppEntity entity = dockerAppEntityRepository.getOne(id);
		Exceptions.throwsException(entity == null, AdminErrorCode.DOCKER_APP_NOT_FOUND, id);
		return smartAssembler.assemble(entity, DockerAppEntity.class, DockerApp.class);
	}

}
