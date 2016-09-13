package lamp.admin.domain.app.base.service;

import lamp.admin.core.app.base.App;
import lamp.admin.core.app.docker.DockerAppContainer;
import lamp.admin.domain.app.base.model.entity.AppType;
import lamp.admin.domain.app.base.model.form.DockerAppCreateForm;
import lamp.admin.domain.app.base.model.form.DockerAppUpdateForm;
import lamp.common.utils.ArrayUtils;
import lamp.common.utils.CollectionUtils;
import lamp.common.utils.StringUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class DockerAppService {

	private ResourceLoader resourceLoader;

	public App newApp(String path, String parentPath, DockerAppCreateForm editForm) {
		App app = new App();

		app.setVersion(Instant.now().toString());
		app.setType(AppType.DOCKER_APP);
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
		container.setHostName(editForm.getHostName());
		container.setForcePullImage(editForm.isForcePullImage());
//		container.setPortDefinitions(editForm.getPortDefinitions());
		container.setPortMappings(split(editForm.getPortMappings()));
		container.setVolumes(split(editForm.getVolumes()));
		container.setEnv(split(editForm.getEnv()));

		container.setEntrypoint(split(editForm.getEntrypoint()));
		container.setCmd(split(editForm.getCmd()));

		app.setContainer(container);

		return app;
	}

	protected List<String> split(String str) {
		List<String> list = new ArrayList<>();
		String[] lines = StringUtils.split(str, '\n');
		if (ArrayUtils.isNotEmpty(lines)) {
			for (String line : lines) {
				if (StringUtils.isNotBlank(line)) {
					list.add(StringUtils.trim(line));
				}
			}
		}
		return list;
	}

	protected String join(List<String> list) {
		if (CollectionUtils.isEmpty(list)) {
			return "";
		}
		return StringUtils.join(list.toArray(new String[list.size()]), '\n');
	}

	public DockerAppUpdateForm getDockerAppUpdateForm(App app) {
		DockerAppUpdateForm editForm = new DockerAppUpdateForm();
		DockerAppContainer container = (DockerAppContainer) app.getContainer();

		editForm.setName(app.getName());
		editForm.setDescription(app.getDescription());


		editForm.setImage(container.getImage());
		editForm.setHostName(container.getHostName());
		editForm.setForcePullImage(container.isForcePullImage());
//		editForm.setPortDefinitions(container.getPortDefinitions());
		editForm.setPortMappings(join(container.getPortMappings()));
		editForm.setVolumes(join(container.getVolumes()));
		editForm.setEnv(join(container.getEnv()));

		editForm.setEntrypoint(join(container.getEntrypoint()));
		editForm.setCmd(join(container.getCmd()));

		return editForm;
	}

}
