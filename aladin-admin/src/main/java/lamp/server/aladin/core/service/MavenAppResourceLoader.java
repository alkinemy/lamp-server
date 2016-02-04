package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.AppResource;
import lamp.server.aladin.core.domain.MavenAppRepo;
import lamp.server.aladin.core.support.maven.ArtifactRepositoryClient;
import lamp.server.aladin.core.support.resource.MavenAppResource;
import org.eclipse.aether.artifact.Artifact;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

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
