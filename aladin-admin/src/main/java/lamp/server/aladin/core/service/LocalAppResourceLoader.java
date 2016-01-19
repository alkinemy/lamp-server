package lamp.server.aladin.core.service;

import com.google.common.collect.Iterables;
import lamp.server.aladin.core.domain.LocalAppRepo;
import lamp.server.aladin.core.domain.LocalAppFile;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.core.exception.LampErrorCode;
import lamp.server.aladin.core.repository.LocalAppFileRepository;
import lamp.server.aladin.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;

@Component
public class LocalAppResourceLoader {

	@Autowired
	private LocalAppFileRepository localAppFileRepository;

	public Resource getResource(LocalAppRepo localAppRepo, String groupId, String artifactId, String version) {
		if (StringUtils.isBlank(version)) {
			version = getLastVersion(groupId, artifactId);
		}
		Optional<LocalAppFile> localAppResourceFromDb = localAppFileRepository.findOneByGroupIdAndArtifactIdAndVersion(groupId, artifactId, version);

		LocalAppFile localAppFile = localAppResourceFromDb.orElseThrow(() -> Exceptions.newException(LampErrorCode.APP_RESOURCE_NOT_FOUND));
		File file = new File(localAppFile.getPathname());
		return new FileSystemResource(file);
	}

	protected String getLastVersion(String groupId, String artifactId) {
		Page<LocalAppFile> resourceList = localAppFileRepository.findAllByGroupIdAndArtifactIdOrderByVersionDesc(groupId, artifactId, new PageRequest(0, 1));
		LocalAppFile lastVersionResource = Iterables.getFirst(resourceList.getContent(), null);
		return lastVersionResource.getVersion();
	}

}
