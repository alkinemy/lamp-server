package lamp.admin.domain.app.base.model.assembler;

import lamp.admin.core.app.base.AppInstance;
import lamp.admin.domain.app.base.model.entity.AppInstanceEntity;
import lamp.common.utils.assembler.AbstractListAssembler;
import lamp.common.utils.assembler.Populater;
import org.springframework.stereotype.Component;

@Component
public class AppInstanceEntityAssembler extends AbstractListAssembler <AppInstance, AppInstanceEntity>
	implements Populater<AppInstance, AppInstanceEntity> {

	@Override protected AppInstanceEntity doAssemble(AppInstance appInstance) {
		AppInstanceEntity entity = new AppInstanceEntity();
		entity.setId(appInstance.getId());
		entity.setName(appInstance.getName());
		entity.setDescription(appInstance.getDescription());

		entity.setAppId(appInstance.getAppId());
		entity.setAppVersion(appInstance.getAppVersion());
		entity.setHostId(appInstance.getHostId());
		entity.setStatus(appInstance.getStatus());

		entity.setMonitored(appInstance.isMonitored());

		return entity;
	}

	@Override public void populate(AppInstance source, AppInstanceEntity target) {
		target.setStatus(source.getStatus());

		target.setMonitored(source.isMonitored());
	}
}
