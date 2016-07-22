package lamp.admin.domain.app.base.model.assembler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lamp.admin.core.app.base.AppInstance;
import lamp.admin.domain.app.base.model.entity.AppInstanceEntity;
import lamp.admin.domain.base.exception.CannotAssembleException;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.web.AdminErrorCode;
import lamp.common.utils.assembler.AbstractListAssembler;
import lamp.common.utils.assembler.Populater;
import org.springframework.stereotype.Component;

@Component
public class AppInstanceEntityAssembler extends AbstractListAssembler <AppInstance, AppInstanceEntity>
	implements Populater<AppInstance, AppInstanceEntity> {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override protected AppInstanceEntity doAssemble(AppInstance appInstance) {
		try {
			AppInstanceEntity entity = new AppInstanceEntity();
			entity.setId(appInstance.getId());
			entity.setName(appInstance.getName());
			entity.setDescription(appInstance.getDescription());

			entity.setAppId(appInstance.getAppId());
			entity.setAppVersion(appInstance.getAppVersion());
			entity.setHostId(appInstance.getHostId());
			entity.setStatus(appInstance.getStatus());

			entity.setData(objectMapper.writeValueAsString(appInstance));

			entity.setMonitored(appInstance.isMonitored());

			return entity;
		} catch (Exception e) {
			throw new CannotAssembleException(e);
		}

	}

	@Override public void populate(AppInstance source, AppInstanceEntity target) {
		try {
			target.setAppVersion(source.getAppVersion());

			target.setStatus(source.getStatus());

			target.setData(objectMapper.writeValueAsString(source));
			target.setMonitored(source.isMonitored());
		} catch (Exception e) {
			throw new CannotAssembleException(e);
		}
	}
}
