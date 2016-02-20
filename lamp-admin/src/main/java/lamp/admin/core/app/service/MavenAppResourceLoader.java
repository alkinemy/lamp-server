package lamp.admin.core.app.service;

import lamp.admin.core.app.domain.AppResource;
import lamp.admin.core.app.domain.MavenAppRepo;
import lamp.admin.core.support.maven.ArtifactRepositoryClient;
import lamp.admin.core.support.resource.MavenAppResource;
import org.eclipse.aether.artifact.Artifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class MavenAppResourceLoader {

	@Autowired
	private MavenAppRepoService mavenAppRepoService;

	public AppResource getResource(MavenAppRepo appRepo,
								   String groupId, String artifactId, String version) {

		ArtifactRepositoryClient artifactRepositoryClient = mavenAppRepoService.getArtifactRepositoryClient(appRepo);
		Artifact artifact = artifactRepositoryClient.getArtifact(groupId, artifactId, version);
		return new MavenAppResource(artifact);
	}

}
