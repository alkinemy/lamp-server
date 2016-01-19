package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.*;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.core.exception.LampErrorCode;
import lamp.server.aladin.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class AppResourceService {

	@Autowired
	private LocalAppResourceLoader localAppResourceLoader;

	@Autowired
	private MavenAppResourceLoader mavenAppResourceLoader;

	public Resource getResource(AppTemplate appTemplate, String version) {
		return getResource(appTemplate, appTemplate.getGroupId(), appTemplate.getAppId(), StringUtils.defaultIfBlank(version, appTemplate.getAppVersion()));
	}

	public Resource getResource(AppTemplate appTemplate, String groupId, String artifactId, String version) {
		if (AppResourceType.LOCAL.equals(appTemplate.getTemplateType())) {
			return localAppResourceLoader.getResource((LocalAppRepo) appTemplate.getAppRepository(), groupId, artifactId, version);
		} else if (AppResourceType.MAVEN.equals(appTemplate.getTemplateType())) {
			return mavenAppResourceLoader.getResource((MavenAppRepo) appTemplate.getAppRepository(), groupId, artifactId, version);
		} else if (AppResourceType.URL.equals(appTemplate.getTemplateType())) {

		}
		throw Exceptions.newException(LampErrorCode.UNSUPPORTED_APP_TEMPLATE_TYPE, appTemplate.getTemplateType());
	}




}
