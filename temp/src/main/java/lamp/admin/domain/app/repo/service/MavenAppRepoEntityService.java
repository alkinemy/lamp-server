package lamp.admin.domain.app.repo.service;

import lamp.admin.config.web.ServerProperties;

import lamp.admin.domain.app.repo.model.entity.MavenAppRepoEntity;
import lamp.admin.domain.support.maven.ArtifactRepositoryClient;
import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.version.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MavenAppRepoEntityService {

	@Autowired
	private ServerProperties serverProperties;

	public List<String> getVersions(MavenAppRepoEntity appRepo, String groupId, String artifactId) {
		ArtifactRepositoryClient artifactRepositoryClient = getArtifactRepositoryClient(appRepo);
		VersionRangeResult versionRangeResult = artifactRepositoryClient.getVersionRangeResult(groupId, artifactId);
		return versionRangeResult.getVersions().stream().map(Version::toString).collect(Collectors.toList());
	}

	protected ArtifactRepositoryClient getArtifactRepositoryClient(MavenAppRepoEntity appRepo) {
		File repositoryDir = new File(serverProperties.getMavenAppRepository());
		ArtifactRepositoryClient artifactRepositoryClient = new ArtifactRepositoryClient(repositoryDir, appRepo.getUrl(), appRepo.getUsername(), appRepo.getPassword());
		return artifactRepositoryClient;
	}

}
