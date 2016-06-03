package lamp.admin.domain.app.base.model.assembler;

import lamp.admin.core.app.base.App;
import lamp.admin.domain.app.base.model.entity.AppEntity;
import lamp.admin.domain.support.json.JsonUtils;
import lamp.common.utils.assembler.AbstractListAssembler;
import org.springframework.stereotype.Component;

@Component
public class AppEnttiyAssembler extends AbstractListAssembler <App, AppEntity> {

	@Override protected AppEntity doAssemble(App app) {
		AppEntity entity = new AppEntity();
		entity.setId(app.getId());
		entity.setName(app.getName());
		entity.setDescription(app.getDescription());
		entity.setGroupId(app.getGroupId());

		entity.setCpu(app.getCpu());
		entity.setMemory(app.getMemory());
		entity.setDiskSpace(app.getDiskSpace());
		entity.setInstances(app.getInstances());

		entity.setData(JsonUtils.stringify(app));
		return entity;
	}

}
