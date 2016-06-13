package lamp.admin.domain.app.base.model.assembler;

import lamp.admin.core.app.base.App;
import lamp.admin.core.app.base.AppInstance;
import lamp.admin.core.host.Host;
import lamp.admin.domain.app.base.model.entity.AppInstanceEntity;
import lamp.admin.domain.app.base.service.AppService;
import lamp.admin.domain.host.service.HostService;
import lamp.common.utils.assembler.AbstractListAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppInstanceAssembler extends AbstractListAssembler <AppInstanceEntity, AppInstance> {

	@Autowired
	private AppService appService;
	@Autowired
	private HostService hostService;

	@Override protected AppInstance doAssemble(AppInstanceEntity entity) {
		AppInstance appInstance = new AppInstance();
		appInstance.setId(entity.getId());
		appInstance.setName(entity.getName());
		appInstance.setDescription(entity.getDescription());
		appInstance.setAppId(entity.getAppId());
		appInstance.setAppVersion(entity.getAppVersion());
		appInstance.setHostId(entity.getHostId());
		appInstance.setStatus(entity.getStatus());

		appInstance.setMonitored(entity.isMonitored());

		Optional<Host> hostOptional = hostService.getHostOptional(entity.getHostId());
		appInstance.setHost(hostOptional.orElse(null));

		return appInstance;
	}

}
