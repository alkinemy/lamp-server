package lamp.admin.domain.old.app.model;

import lamp.admin.domain.agent.model.TargetServer;
import lamp.admin.domain.old.app.model.AppTemplate;
import lamp.admin.domain.old.app.model.ManagedApp;
import lamp.admin.domain.old.app.model.ManagedAppDto;
import lamp.common.utils.assembler.AbstractListAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ManagedAppDtoAssembler extends AbstractListAssembler<ManagedApp, ManagedAppDto> {

	@Override protected ManagedAppDto doAssemble(ManagedApp managedApp) {
		ManagedAppDto dto = new ManagedAppDto();
		BeanUtils.copyProperties(managedApp, dto, "targetServer", "appTemplate");
		TargetServer targetServer = managedApp.getTargetServer();
		dto.setHostname(targetServer.getHostname());
		dto.setAddress(targetServer.getAddress());

		AppTemplate appTemplate = managedApp.getAppTemplate();
		if (appTemplate != null) {
			dto.setAppTemplateId(appTemplate.getId());
			dto.setAppTemplateName(appTemplate.getName());
		}
		return dto;
	}
}
