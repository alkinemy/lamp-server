package lamp.admin.core.app.service;

import lamp.admin.core.app.domain.AppResource;
import lamp.admin.core.app.domain.MavenAppRepo;
import lamp.admin.core.support.maven.ArtifactRepositoryClient;
import lamp.admin.core.support.resource.MavenAppResource;
import org.eclipse.aether.artifact.Artifact;

import java.io.File;

public class MavenAppResourceLoader {

	private final String mavenAppRepository;

	public MavenAppResourceLoader(String mavenAppRepository) {
		this.mavenAppRepository = mavenAppRepository;
	}

	public AppResource getResource(MavenAppRepo appRepo,
								   String groupId, String artifactId, String version) {

		File repositoryDir = new File(mavenAppRepository);
		ArtifactRepositoryClient artifactRepositoryClient = new ArtifactRepositoryClient(repositoryDir, appRepo.getUrl(), appRepo.getUsername(), appRepo.getPassword());

		Artifact artifact = artifactRepositoryClient.getArtifact(groupId, artifactId, version);
		return new MavenAppResource(artifact);
	}

}
