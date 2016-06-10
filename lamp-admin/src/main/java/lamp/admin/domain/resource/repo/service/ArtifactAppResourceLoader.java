package lamp.admin.domain.resource.repo.service;


import lamp.admin.core.app.simple.resource.ArtifactAppResource;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.admin.domain.resource.repo.model.entity.AppRepoEntity;
import lamp.admin.domain.resource.repo.model.entity.LocalAppRepoEntity;
import lamp.admin.domain.resource.repo.model.entity.MavenAppRepoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ArtifactAppResourceLoader {

	@Autowired
	private AppRepoService appRepoService;

	@Autowired
	private LocalAppResourceLoader localAppResourceLoader;

	@Autowired
	private MavenAppResourceLoader mavenAppResourceLoader;


	public Resource getResource(ArtifactAppResource appResource) {
		String repositoryId = appResource.getRepositoryId();
		String groupId = appResource.getGroupId();
		String artifactId = appResource.getArtifactId();
		String version = appResource.getVersion();

		AppRepoEntity appRepoEntity = appRepoService.getAppRepo(repositoryId);

		if (appRepoEntity instanceof LocalAppRepoEntity) {
			return localAppResourceLoader.getResource((LocalAppRepoEntity) appRepoEntity, groupId, artifactId, version);
		} else if (appRepoEntity instanceof MavenAppRepoEntity) {
			return mavenAppResourceLoader.getResource((MavenAppRepoEntity) appRepoEntity, groupId, artifactId, version);
		}
		throw Exceptions.newException(LampErrorCode.UNSUPPORTED_APP_REPOSITORY_TYPE, appRepoEntity.getClass());
	}

}
