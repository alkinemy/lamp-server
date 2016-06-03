package lamp.admin.domain.app.repo.service;


import lamp.admin.core.app.simple.resource.AppResource;
import lamp.admin.domain.app.repo.model.entity.AppRepoEntity;
import lamp.admin.domain.app.repo.model.entity.LocalAppRepoEntity;
import lamp.admin.domain.app.repo.model.entity.MavenAppRepoEntity;
import lamp.admin.domain.app.repo.model.entity.UrlAppRepoEntity;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppResourceLoader {

	@Autowired
	private LocalAppResourceLoader localAppResourceLoader;

	@Autowired
	private MavenAppResourceLoader mavenAppResourceLoader;


	public AppResource getResource(AppRepoEntity appRepoEntity) {
//		if (appRepoEntity instanceof LocalAppRepoEntity) {
//			return localAppResourceLoader.getResource((LocalAppRepoEntity) appRepoEntity, groupId, artifactId, version);
//		} else if (appRepoEntity instanceof MavenAppRepoEntity) {
//			return mavenAppResourceLoader.getResource((MavenAppRepoEntity) appRepoEntity, groupId, artifactId, version);
//		} else if (appRepoEntity instanceof UrlAppRepoEntity) {
//
//		}
//		throw Exceptions.newException(LampErrorCode.UNSUPPORTED_APP_TEMPLATE_TYPE, appTemplate.getResourceType());
		return null;
	}




}
