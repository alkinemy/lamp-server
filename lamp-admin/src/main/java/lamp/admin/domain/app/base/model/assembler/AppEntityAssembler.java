package lamp.admin.domain.app.base.model.assembler;

import lamp.admin.core.app.base.App;
import lamp.admin.domain.app.base.model.entity.AppEntity;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.utils.FilenameUtils;
import lamp.common.utils.StringUtils;
import lamp.common.utils.assembler.AbstractListAssembler;
import lamp.common.utils.assembler.Populater;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AppEntityAssembler extends AbstractListAssembler<App, AppEntity> implements Populater<App, AppEntity> {

	@Override protected AppEntity doAssemble(App app) {
		app.setId(UUID.randomUUID().toString());
		String parentPath = FilenameUtils.normalizeNoEndSeparator(app.getParentPath());
		app.setParentPath(parentPath);
		String path = (StringUtils.isBlank(parentPath) ? app.getName() : parentPath + "/" + app.getName());
		app.setPath(path);

		AppEntity entity = new AppEntity();
		entity.setId(app.getId());
		entity.setVersion(app.getVersion());
		entity.setType(app.getType());
		entity.setPath(app.getPath());
		entity.setParentPath(app.getParentPath());
		entity.setName(app.getName());
		entity.setDescription(app.getDescription());

		entity.setCpu(app.getCpu());
		entity.setMemory(app.getMemory());
		entity.setDiskSpace(app.getDiskSpace());
		entity.setInstances(app.getInstances());

		entity.setData(JsonUtils.stringify(app));
		return entity;
	}

	@Override public void populate(App app, AppEntity entity) {
//		entity.setId(app.getId());
		entity.setVersion(app.getVersion());
//		entity.setType(app.getType());
//		entity.setPath(app.getPath());
//		entity.setParentPath(parentPath);
		entity.setName(app.getName());
		entity.setDescription(app.getDescription());

		entity.setCpu(app.getCpu());
		entity.setMemory(app.getMemory());
		entity.setDiskSpace(app.getDiskSpace());
		entity.setInstances(app.getInstances());

		entity.setData(JsonUtils.stringify(app));
	}
}
