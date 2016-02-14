package lamp.admin.core.app.service;

import lamp.admin.core.app.domain.*;
import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.core.base.exception.LampErrorCode;
import lamp.admin.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppResourceService {

	@Autowired
	private LocalAppResourceLoader localAppResourceLoader;

	@Autowired
	private MavenAppResourceLoader mavenAppResourceLoader;

	public AppResource getResource(AppTemplate appTemplate, String version) {
		return getResource(appTemplate, appTemplate.getGroupId(), appTemplate.getArtifactId(), StringUtils.defaultIfBlank(version, appTemplate.getVersion()));
	}

	public AppResource getResource(AppTemplate appTemplate, String groupId, String artifactId, String version) {
		if (AppResourceType.LOCAL.equals(appTemplate.getResourceType())) {
			return localAppResourceLoader.getResource((LocalAppRepo) appTemplate.getAppRepository(), groupId, artifactId, version);
		} else if (AppResourceType.MAVEN.equals(appTemplate.getResourceType())) {
			return mavenAppResourceLoader.getResource((MavenAppRepo) appTemplate.getAppRepository(), groupId, artifactId, version);
		} else if (AppResourceType.URL.equals(appTemplate.getResourceType())) {

		}
		throw Exceptions.newException(LampErrorCode.UNSUPPORTED_APP_TEMPLATE_TYPE, appTemplate.getResourceType());
	}




}
