package lamp.admin.domain.app.base.service;

import lamp.admin.core.app.base.App;
import lamp.admin.core.app.docker.DockerAppContainer;
import lamp.admin.domain.app.base.model.entity.AppType;
import lamp.admin.domain.app.base.model.form.DockerAppCreateForm;
import lamp.admin.domain.app.base.model.form.DockerAppUpdateForm;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DockerAppService {

	private ResourceLoader resourceLoader;

	public App newApp(String path, String parentPath, DockerAppCreateForm editForm) {
		App app = new App();

		app.setVersion(Instant.now().toString());
		app.setType(AppType.APP);
		app.setPath(path);
		app.setParentPath(parentPath);
		app.setName(editForm.getName());
		app.setDescription(editForm.getDescription());

//		Map<String, Object> parameters = new LinkedHashMap<>();
//		parameters.put("parametersType", editForm.getParametersType());
//		parameters.put("parameters", editForm.getParameters());
//		parameters.put("properties", editForm.getProperties());
//		app.setParameters(parameters);

		DockerAppContainer container = new DockerAppContainer();
		container.setName(app.getName());
		container.setImage(editForm.getImage());
		container.setForcePullImage(editForm.isForcePullImage());
		container.setPortDefinitions(editForm.getPortDefinitions());
		container.setPortMappings(editForm.getPortMappings());
		container.setVolumes(editForm.getVolumes());
		container.setEnv(editForm.getEnv());

		app.setContainer(container);

		return app;
	}

	public DockerAppUpdateForm getDockerAppUpdateForm(App app) {
		DockerAppUpdateForm editForm = new DockerAppUpdateForm();
		DockerAppContainer container = (DockerAppContainer) app.getContainer();

		editForm.setName(app.getName());
		editForm.setDescription(app.getDescription());


		editForm.setImage(container.getImage());
		editForm.setForcePullImage(container.isForcePullImage());
		editForm.setPortDefinitions(container.getPortDefinitions());
		editForm.setPortMappings(container.getPortMappings());
		editForm.setVolumes(container.getVolumes());
		editForm.setEnv(container.getEnv());

		return editForm;
	}

}
