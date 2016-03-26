package lamp.admin.domain.app.model;

import lamp.admin.domain.agent.model.TargetServer;
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
		return dto;
	}
}
