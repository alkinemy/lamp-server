package lamp.admin.core.app.service;

import com.google.common.collect.Iterables;
import lamp.admin.core.app.domain.AppResource;
import lamp.admin.core.app.domain.LocalAppFile;
import lamp.admin.core.app.domain.LocalAppRepo;
import lamp.admin.core.app.repository.LocalAppFileRepository;
import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.core.base.exception.LampErrorCode;
import lamp.admin.core.support.resource.LocalFileAppResource;
import lamp.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;

@Component
public class LocalAppResourceLoader {

	@Autowired
	private LocalAppFileRepository localAppFileRepository;

	public AppResource getResource(LocalAppRepo localAppRepo, String groupId, String artifactId, String version) {
		String repositoryId = localAppRepo.getId();
		if (StringUtils.isBlank(version)) {
			version = getLastVersion(repositoryId, groupId, artifactId);
		}
		Optional<LocalAppFile> localAppResourceFromDb = localAppFileRepository.findOneByRepositoryIdAndGroupIdAndArtifactIdAndBaseVersion(repositoryId, groupId, artifactId, version);

		LocalAppFile localAppFile = localAppResourceFromDb.orElseThrow(() -> Exceptions.newException(LampErrorCode.APP_RESOURCE_NOT_FOUND));
		File file = new File(localAppFile.getPathname());
		return new LocalFileAppResource(file, localAppFile.getFilename(), groupId, artifactId, version);
	}

	protected String getLastVersion(String repositoryId, String groupId, String artifactId) {
		// TODO 정상적으로 작동하도록 수정 바람
		Page<LocalAppFile> resourceList = localAppFileRepository.findAllByRepositoryIdAndGroupIdAndArtifactIdOrderByVersionDesc(repositoryId, groupId, artifactId, new PageRequest(0, 1));
		LocalAppFile lastVersionResource = Iterables.getFirst(resourceList.getContent(), null);
		return lastVersionResource.getBaseVersion();
	}

}
