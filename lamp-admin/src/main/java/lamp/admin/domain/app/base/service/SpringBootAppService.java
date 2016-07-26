package lamp.admin.domain.app.base.service;

import lamp.admin.LampAdminConstants;
import lamp.admin.core.app.base.App;
import lamp.admin.core.app.base.HealthEndpoint;
import lamp.admin.core.app.base.MetricsEndpoint;
import lamp.admin.core.app.simple.AppProcessType;
import lamp.admin.core.app.simple.SimpleAppContainer;
import lamp.admin.core.app.simple.resource.AppResource;
import lamp.admin.core.app.simple.resource.AppResourceType;
import lamp.admin.core.app.simple.resource.ArtifactAppResource;
import lamp.admin.core.app.simple.resource.UrlAppResource;
import lamp.admin.core.script.ScriptCommand;
import lamp.admin.core.script.ScriptFileCreateCommand;
import lamp.admin.domain.app.base.model.SpringBootAppProperties;
import lamp.admin.domain.app.base.model.entity.AppType;
import lamp.admin.domain.app.base.model.form.ParametersType;
import lamp.admin.domain.app.base.model.form.SpringBootAppCreateForm;
import lamp.admin.domain.app.base.model.form.SpringBootAppUpdateForm;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.collector.core.base.EndpointProtocol;
import lamp.common.utils.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpringBootAppService implements ResourceLoaderAware {

	private ResourceLoader resourceLoader;

	@Autowired
	private SpringBootAppProperties springBootAppProperties;

	public App newApp(String path, String parentPath, SpringBootAppCreateForm editForm) {
		App app = new App();

		app.setVersion(Instant.now().toString());
		app.setType(AppType.SPRING_BOOT_APP);
		app.setPath(path);
		app.setParentPath(parentPath);

		return newApp(app, editForm);
	}

	public App newApp(App app, SpringBootAppCreateForm editForm) {
		app.setName(editForm.getName());
		app.setDescription(editForm.getDescription());

		Map<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("parametersType", editForm.getParametersType());
		parameters.put("parameters", editForm.getParameters());
		parameters.put("properties", editForm.getProperties());
		parameters.put("serverPort", editForm.getServerPort());
		parameters.put("managementPort", editForm.getManagementPort());

		app.setParameters(parameters);

		SimpleAppContainer container = new SimpleAppContainer();
		container.setName(app.getName());

		String artifactId = StringUtils.defaultIfBlank(editForm.getArtifactId(), editForm.getName());

		container.setProcessType(ObjectUtils.defaultIfNull(editForm.getProcessType(), AppProcessType.DAEMON));
		container.setAppDirectory(editForm.getAppDirectory());
		container.setWorkDirectory(StringUtils.defaultIfBlank(editForm.getWorkDirectory(), "${appDirectory}"));
		container.setLogDirectory(editForm.getLogDirectory());

		container.setPidFile(StringUtils.defaultIfBlank(editForm.getPidFile(), "${workDirectory}/" + artifactId + ".pid"));
		container.setStdOutFile(StringUtils.defaultIfBlank(editForm.getStdOutFile(), "stdout.log"));
		container.setStdErrFile(StringUtils.defaultIfBlank(editForm.getStdErrFile(), "stderr.log"));

		container.setCommandShell(editForm.getCommandShell());
		container.setStartCommandLine(StringUtils.defaultIfBlank(editForm.getStartCommandLine(), "./" + artifactId + ".sh start"));
		container.setStartTimeout(editForm.getStartTimeout());
		container.setStopCommandLine(StringUtils.defaultIfBlank(editForm.getStopCommandLine(), "./" + artifactId + ".sh stop"));
		container.setStopTimeout(editForm.getStopTimeout());

		container.setAppResource(getAppResource(editForm));
		container.setInstallFilename(artifactId + ".jar"); // FIXME 수정과 불일치

		// Health
		container.setHealthEndpointEnabled(editForm.isHealthEndpointEnabled());

		HealthEndpoint healthEndpoint = new HealthEndpoint();
		healthEndpoint.setProtocol(StringUtils.defaultIfBlank(editForm.getHealthEndpointProtocol(), EndpointProtocol.HTTP));
		healthEndpoint.setPort(ObjectUtils.defaultIfNull(editForm.getHealthEndpointPort(),
														 ObjectUtils.defaultIfNull(editForm.getManagementPort(), editForm.getServerPort())));
		healthEndpoint.setPath(StringUtils.defaultIfBlank(editForm.getHealthEndpointPath(),"/health"));
		healthEndpoint.setTimeoutSeconds(editForm.getHealthEndpointTimeoutSeconds());
		container.setHealthEndpoint(healthEndpoint);

		// Metrics
		container.setMetricsEndpointEnabled(editForm.isMetricsEndpointEnabled());

		MetricsEndpoint metricsEndpoint = new MetricsEndpoint();
		metricsEndpoint.setProtocol(StringUtils.defaultIfBlank(editForm.getMetricsEndpointProtocol(), EndpointProtocol.HTTP));
		metricsEndpoint.setPort(ObjectUtils.defaultIfNull(editForm.getMetricsEndpointPort(),
														  ObjectUtils.defaultIfNull(editForm.getManagementPort(), editForm.getServerPort())));
		metricsEndpoint.setPath(StringUtils.defaultIfBlank(editForm.getMetricsEndpointPath(),"/metrics"));
		metricsEndpoint.setTimeoutSeconds(editForm.getMetricsEndpointTimeoutSeconds());
		container.setMetricsEndpoint(metricsEndpoint);


		container.setParameters(getParameters(editForm, artifactId)); // FIXME 수정과 불일치

		String shellContent = getShellContent(editForm);

		List<ScriptCommand> scriptCommands = getInstallScriptCommands(artifactId, editForm.getProperties(), shellContent);
		container.setScriptCommands(scriptCommands);

		app.setContainer(container);

		return app;
	}

	protected String getShellContent(SpringBootAppCreateForm editForm) {
		// FIXME Host의 JAVA_HOME 변수를 가져와서 JAVA 경로를 결정하게 변경 필요
		String shellContent;
		try {
			String location = StringUtils.defaultIfBlank(editForm.getShellScriptLocation(), springBootAppProperties.getShellScriptLocation());
			Resource resource = resourceLoader.getResource(location);
			shellContent = IOUtils.toString(resource.getInputStream(), LampAdminConstants.DEFAULT_CHARSET);
		} catch (IOException e) {
			throw Exceptions.newException(LampErrorCode.SHELL_FILE_NOT_FOUND, e);
		}
		return shellContent;
	}

	public SpringBootAppUpdateForm getSpringBootAppUpdateForm(App app) {
		SpringBootAppUpdateForm editForm = new SpringBootAppUpdateForm();
		SimpleAppContainer container = (SimpleAppContainer) app.getContainer();

		editForm.setName(app.getName());
		editForm.setDescription(app.getDescription());

		editForm.setProcessType(container.getProcessType());
		editForm.setAppDirectory(container.getAppDirectory());
		editForm.setWorkDirectory(container.getWorkDirectory());
		editForm.setLogDirectory(container.getLogDirectory());

		editForm.setPidFile(container.getPidFile());
		editForm.setStdOutFile(container.getStdOutFile());
		editForm.setStdErrFile(container.getStdErrFile());

		editForm.setCommandShell(container.getCommandShell());
		editForm.setStartCommandLine(container.getStartCommandLine());
		editForm.setStartTimeout(container.getStartTimeout());
		editForm.setStopCommandLine(container.getStopCommandLine());
		editForm.setStopTimeout(container.getStopTimeout());

		populateAppResource(editForm, container.getAppResource());

		editForm.setAppFilename(container.getInstallFilename());

		editForm.setHealthEndpointEnabled(container.isHealthEndpointEnabled());
		editForm.setMetricsEndpointEnabled(container.isMetricsEndpointEnabled());

		Map<String, Object> parameters = app.getParameters();
		if (parameters != null) {
			editForm.setParametersType((ParametersType) parameters.get("parametersType"));
			editForm.setParameters((String) parameters.get("parameters"));
			editForm.setProperties((String) parameters.get("properties"));

			editForm.setServerPort((Integer) parameters.get("serverPort"));
			editForm.setManagementPort((Integer) parameters.get("managementPort"));
		}

		return editForm;
	}

	protected Map<String, Object> getParameters(SpringBootAppCreateForm editForm, String artifactId) {
		Map<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("artifactId", artifactId);
		parameters.put("jvmOpts", editForm.getJvmOpts());
		parameters.put("springOpts", editForm.getSpringOpts());

		parameters.put("serverPort", editForm.getServerPort());
		parameters.put("managementPort", editForm.getManagementPort());

		if (StringUtils.isNotBlank(editForm.getParameters())) {
			if (ParametersType.JSON == editForm.getParametersType()) {
				parameters.putAll(JsonUtils.parse(editForm.getParameters(), LinkedHashMap.class));
			} else {
				Properties properties = new Properties();
				try (Reader reader = new StringReader(editForm.getParameters())) {
					properties.load(reader);

					parameters.putAll(properties.stringPropertyNames().stream()
						.collect(Collectors.toMap(key -> key, key -> properties.getProperty(key))));

				} catch (IOException e) {
					throw Exceptions.newException(LampErrorCode.INVALID_PARAMETERS);
				}
			}
		}
		return parameters;
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

	protected void populateAppResource(SpringBootAppUpdateForm editForm, AppResource appResource) {
		if (appResource instanceof ArtifactAppResource) {
			ArtifactAppResource artifactAppResource = (ArtifactAppResource) appResource;
			editForm.setRepositoryId(artifactAppResource.getRepositoryId());
			editForm.setGroupId(artifactAppResource.getGroupId());
			editForm.setArtifactId(artifactAppResource.getArtifactId());
			editForm.setVersion(artifactAppResource.getVersion());
		} else if (appResource instanceof UrlAppResource) {
			UrlAppResource urlAppResource = (UrlAppResource) appResource;
			editForm.setResourceUrl(urlAppResource.getResourceUrl());
		}
	}

	protected List<ScriptCommand> getInstallScriptCommands(String artifactId,
														   String propertiesContent,
														   String shellContent) {
		List<ScriptCommand> scriptCommandEntities = new ArrayList<>();
		if (StringUtils.isNotBlank(propertiesContent)){
			String content = propertiesContent;

			ScriptFileCreateCommand fileCreateCommandDto = new ScriptFileCreateCommand();
			fileCreateCommandDto.setFilename(artifactId + ".properties");
			fileCreateCommandDto.setContent(content);

			scriptCommandEntities.add(fileCreateCommandDto);
		}
		if (StringUtils.isNotBlank(shellContent)) {
			String content = shellContent;

			ScriptFileCreateCommand fileCreateCommandDto = new ScriptFileCreateCommand();
			fileCreateCommandDto.setFilename(artifactId + ".sh");
			fileCreateCommandDto.setContent(content);
			fileCreateCommandDto.setExecutable(true);

			scriptCommandEntities.add(fileCreateCommandDto);
		}
		return scriptCommandEntities;
	}

	@Override public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}


}
