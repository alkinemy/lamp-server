package lamp.admin.domain.app.base.model.assembler;

import lamp.admin.core.app.base.App;
import lamp.admin.domain.app.base.model.entity.AppEntity;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.utils.FilenameUtils;
import lamp.common.utils.StringUtils;
import lamp.common.utils.assembler.AbstractListAssembler;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class AppEntityAssembler extends AbstractListAssembler <App, AppEntity> {

	@Override protected AppEntity doAssemble(App app) {
		String path = FilenameUtils.normalizeNoEndSeparator(app.getPath());
		app.setId((StringUtils.isBlank(path) ? app.getName() : path  + "/" + app.getName()));

		AppEntity entity = new AppEntity();
		entity.setId(app.getId());
		entity.setType(app.getType());
		entity.setPath(path);
		entity.setName(app.getName());
		entity.setDescription(app.getDescription());

		entity.setCpu(app.getCpu());
		entity.setMemory(app.getMemory());
		entity.setDiskSpace(app.getDiskSpace());
		entity.setInstances(app.getInstances());

		entity.setData(JsonUtils.stringify(app));
		return entity;
	}

}
