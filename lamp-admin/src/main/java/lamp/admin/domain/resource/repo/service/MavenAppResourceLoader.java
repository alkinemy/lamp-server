package lamp.admin.domain.resource.repo.service;


import lamp.admin.domain.resource.repo.model.entity.MavenAppRepoEntity;
import lamp.admin.domain.support.maven.ArtifactRepositoryClient;
import org.eclipse.aether.artifact.Artifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class MavenAppResourceLoader {

	@Autowired
	private MavenAppRepoEntityService mavenAppRepoEntityService;

	public Resource getResource(MavenAppRepoEntity appRepo,
								String groupId, String artifactId, String version) {

		ArtifactRepositoryClient artifactRepositoryClient = mavenAppRepoEntityService.getArtifactRepositoryClient(appRepo);
		Artifact artifact = artifactRepositoryClient.getArtifact(groupId, artifactId, version);
		return new FileSystemAppResource(artifact.getFile(), artifact.getGroupId(), artifact.getArtifactId(), artifact.getBaseVersion());
	}

}
