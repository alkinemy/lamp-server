package lamp.admin.core.app.domain;

import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.core.base.exception.LampErrorCode;
import lamp.admin.utils.assembler.AbstractListAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AppInstallCommandDtoAssembler extends AbstractListAssembler<AppInstallCommand, AppInstallCommandDto> {

	@Override protected AppInstallCommandDto doAssemble(AppInstallCommand appInstallCommand) {
		AppInstallCommandDto appInstallCommandDto = null;
		if (appInstallCommand instanceof AppInstallExecuteCommand) {
			appInstallCommandDto = new AppInstallExecuteCommandDto();
		} else if (appInstallCommand instanceof AppInstallFileCreateCommand) {
			appInstallCommandDto = new AppInstallFileCreateCommandDto();
		} else if (appInstallCommand instanceof AppInstallFileRemoveCommand) {
			appInstallCommandDto = new AppInstallFileRemoveCommandDto();
		} else {
			throw Exceptions.newException(LampErrorCode.UNSUPPORTED_APP_INSTALL_COMMAND_TYPE);
		}
		BeanUtils.copyProperties(appInstallCommand, appInstallCommandDto);
		return appInstallCommandDto;
	}

}
