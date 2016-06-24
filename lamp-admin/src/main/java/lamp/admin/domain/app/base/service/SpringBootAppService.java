package lamp.admin.domain.app.base.service;

import lamp.admin.LampAdminConstants;
import lamp.admin.core.app.base.App;
import lamp.admin.core.app.simple.AppProcessType;
import lamp.admin.core.app.simple.SimpleAppContainer;
import lamp.admin.core.app.simple.resource.AppResource;
import lamp.admin.core.app.simple.resource.AppResourceType;
import lamp.admin.core.app.simple.resource.ArtifactAppResource;
import lamp.admin.core.app.simple.resource.UrlAppResource;
import lamp.admin.core.script.ScriptCommand;
import lamp.admin.core.script.ScriptFileCreateCommand;
import lamp.admin.domain.app.base.model.entity.AppType;
import lamp.admin.domain.app.base.model.form.ParametersType;
import lamp.admin.domain.app.base.model.form.SpringBootAppCreateForm;
import lamp.admin.domain.app.base.model.form.SpringBootAppUpdateForm;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.utils.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
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

	public App newApp(String path, String parentPath, SpringBootAppCreateForm editForm) {
		App app = new App();

		app.setVersion(Instant.now().toString());
		app.setType(AppType.SPRING_BOOT_APP);
		app.setPath(path);
		app.setParentPath(parentPath);
		app.setName(editForm.getName());
		app.setDescription(editForm.getDescription());

		Map<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("parametersType", editForm.getParametersType());
		parameters.put("parameters", editForm.getParameters());
		parameters.put("properties", editForm.getProperties());
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
		container.setStopCommandLine(StringUtils.defaultIfBlank(editForm.getStopCommandLine(), "./" + artifactId + ".sh stop"));

		container.setAppResource(getAppResource(editForm));
		container.setInstallFilename(artifactId + ".jar"); // FIXME 수정과 불일치

		container.setParameters(getParameters(editForm, artifactId)); // FIXME 수정과 불일치
		List<ScriptCommand> scriptCommands = getInstallScriptCommands(artifactId, editForm.getProperties(), editForm.getShellFilePath());
		container.setScriptCommands(scriptCommands);

		app.setContainer(container);

		return app;
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
		editForm.setStopCommandLine(container.getStopCommandLine());

		populateAppResource(editForm, container.getAppResource());

		editForm.setAppFilename(container.getInstallFilename());

		Map<String, Object> parameters = app.getParameters();
		if (parameters != null) {
			editForm.setParametersType((ParametersType) parameters.get("parametersType"));
			editForm.setParameters((String) parameters.get("parameters"));
			editForm.setProperties((String) parameters.get("properties"));
		}

		return editForm;
	}

	protected Map<String, Object> getParameters(SpringBootAppCreateForm editForm, String artifactId) {
		Map<String, Object> parameters = new LinkedHashMap<>();
		parameters.put("artifactId", artifactId);
		parameters.put("jvmOpts", editForm.getJvmOpts());
		parameters.put("springOpts", editForm.getSpringOpts());

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
														   String properties,
														   String shellFilePath) {
		List<ScriptCommand> scriptCommandEntities = new ArrayList<>();
		if (StringUtils.isNotBlank(properties)){
			String content = properties;

			ScriptFileCreateCommand fileCreateCommandDto = new ScriptFileCreateCommand();
			fileCreateCommandDto.setFilename(artifactId + ".properties");
			fileCreateCommandDto.setContent(content);

			scriptCommandEntities.add(fileCreateCommandDto);
		}
		try {
			Resource resource = resourceLoader.getResource(shellFilePath);
			String content = IOUtils.toString(resource.getInputStream(), LampAdminConstants.DEFAULT_CHARSET);

			ScriptFileCreateCommand fileCreateCommandDto = new ScriptFileCreateCommand();
			fileCreateCommandDto.setFilename(artifactId + ".sh");
			fileCreateCommandDto.setContent(content);
			fileCreateCommandDto.setExecutable(true);

			scriptCommandEntities.add(fileCreateCommandDto);
		} catch (IOException e) {
			throw Exceptions.newException(LampErrorCode.SHELL_FILE_NOT_FOUND, e);
		}
		return scriptCommandEntities;
	}

	@Override public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}


}
