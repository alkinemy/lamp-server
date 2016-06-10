package lamp.admin.domain.resource.repo.service;


import lamp.admin.core.app.simple.resource.AppResource;
import lamp.admin.core.app.simple.resource.ArtifactAppResource;
import lamp.admin.core.app.simple.resource.UrlAppResource;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class AppResourceLoader {

	@Autowired
	private ArtifactAppResourceLoader artifactAppResourceLoader;

	@Autowired
	private UrlAppResourceLoader urlAppResourceLoader;

	public Resource getResource(AppResource appResource) {
		if (appResource instanceof ArtifactAppResource) {
			return artifactAppResourceLoader.getResource((ArtifactAppResource) appResource);
		} else if (appResource instanceof UrlAppResource) {
			return urlAppResourceLoader.getResource((UrlAppResource) appResource);
		}

		throw Exceptions.newException(LampErrorCode.UNSUPPORTED_APP_RESOURCE_TYPE, appResource.getClass());
	}

}
