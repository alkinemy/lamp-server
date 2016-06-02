package lamp.admin.domain.app.repo.service;


import lamp.admin.core.app.simple.resource.AppResource;
import lamp.admin.core.app.simple.resource.MavenAppResource;

import lamp.admin.domain.app.repo.model.entity.MavenAppRepoEntity;
import lamp.admin.domain.support.maven.ArtifactRepositoryClient;
import org.eclipse.aether.artifact.Artifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MavenAppResourceLoader {

	@Autowired
	private MavenAppRepoEntityService mavenAppRepoEntityService;

	public AppResource getResource(MavenAppRepoEntity appRepo,
								   String groupId, String artifactId, String version) {

		ArtifactRepositoryClient artifactRepositoryClient = mavenAppRepoEntityService.getArtifactRepositoryClient(appRepo);
		Artifact artifact = artifactRepositoryClient.getArtifact(groupId, artifactId, version);
		return new MavenAppResource(artifact);
	}

}
