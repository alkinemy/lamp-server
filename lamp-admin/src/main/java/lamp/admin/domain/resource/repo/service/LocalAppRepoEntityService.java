package lamp.admin.domain.resource.repo.service;


import lamp.admin.domain.resource.repo.model.entity.LocalAppFileEntity;
import lamp.admin.domain.resource.repo.model.entity.LocalAppRepoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalAppRepoEntityService {

	@Autowired
	private LocalAppFileService localAppFileService;

	public List<String> getVersions(LocalAppRepoEntity localAppRepoEntity, String groupId, String artifactId) {
		List<LocalAppFileEntity> localAppFiles = localAppFileService.getLocalAppFiles(localAppRepoEntity.getId(), groupId, artifactId);
		return localAppFiles.stream().map(LocalAppFileEntity::getBaseVersion).collect(Collectors.toList());
	}

}
