package lamp.admin.domain.app.base.service;

import lamp.admin.core.app.base.App;
import lamp.admin.core.app.base.KeyValuePair;
import lamp.admin.core.app.jar.SpringBootAppContainer;
import lamp.admin.core.app.simple.AppProcessType;
import lamp.admin.core.app.simple.resource.AppResource;
import lamp.admin.core.app.simple.resource.AppResourceType;
import lamp.admin.core.app.simple.resource.ArtifactAppResource;
import lamp.admin.core.app.simple.resource.UrlAppResource;
import lamp.admin.domain.app.base.model.entity.AppType;
import lamp.admin.domain.app.base.model.form.ParametersType;
import lamp.admin.domain.app.base.model.form.SpringBootAppCreateForm;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.admin.domain.support.json.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SpringBootAppService {

	public App newApp(String path, SpringBootAppCreateForm editForm) {
		App app = new App();
		app.setType(AppType.APP);
		app.setPath(path);
		app.setName(editForm.getName());
		app.setDescription(editForm.getDescription());

		SpringBootAppContainer container = new SpringBootAppContainer();

		String artifactId = StringUtils.defaultIfBlank(editForm.getArtifactId(), editForm.getName());

		container.setProcessType(ObjectUtils.defaultIfNull(editForm.getProcessType(), AppProcessType.DAEMON));
		container.setAppDirectory(editForm.getAppDirectory());
		container.setWorkDirectory(StringUtils.defaultIfBlank(editForm.getWorkDirectory(), "${appDirectory}"));
		container.setLogDirectory(editForm.getLogDirectory());

		container.setPidFile(StringUtils.defaultIfBlank(editForm.getPidFile(), artifactId + ".pid"));
		container.setStdOutFile(StringUtils.defaultIfBlank(editForm.getStdOutFile(), "stdout.log"));
		container.setStdErrFile(StringUtils.defaultIfBlank(editForm.getStdErrFile(), "stderr.log"));

		container.setCommandShell(editForm.getCommandShell());
		container.setStartCommandLine(StringUtils.defaultIfBlank(editForm.getStartCommandLine(), "./" + artifactId + ".sh start"));
		container.setStopCommandLine(StringUtils.defaultIfBlank(editForm.getStopCommandLine(), "./" + artifactId + ".sh stop"));

		container.setAppResource(getAppResource(editForm));
		container.setInstallFilename(artifactId + ".jar");

		container.setParameters(getParameters(editForm));

		app.setContainer(container);

		return app;
	}

	protected Map<String, Object> getParameters(SpringBootAppCreateForm editForm) {
		if (StringUtils.isNotBlank(editForm.getParameters())) {
			if (ParametersType.JSON == editForm.getParametersType()) {
								return JsonUtils.parse(editForm.getParameters(), LinkedHashMap.class);
			} else {
				Properties properties = new Properties();
				try (Reader reader = new StringReader(editForm.getParameters())) {
					properties.load(reader);

					return properties.stringPropertyNames().stream()
						.collect(Collectors.toMap(key -> key, key -> properties.getProperty(key)));

				} catch (IOException e) {
					throw Exceptions.newException(LampErrorCode.INVALID_PARAMETERS);
				}
			}
		}
		return null;
	}

	protected AppResource getAppResource(SpringBootAppCreateForm editForm) {
		AppResourceType resourceType = editForm.getResourceType();
		if (AppResourceType.ARTIFACT == resourceType) {
			ArtifactAppResource appResource = new ArtifactAppResource();
			appResource.setRepositoryId(editForm.getRepositoryId());
			appResource.setGroupId(editForm.getGroupId());
			appResource.setArtifactId(editForm.getArtifactId());
			appResource.setVersion(editForm.getVersion());

			return appResource;
		} else if (AppResourceType.URL == resourceType) {
			UrlAppResource appResource = new UrlAppResource();
			appResource.setResourceUrl(editForm.getResourceUrl());

			return appResource;
		}
		return null;
	}

}
