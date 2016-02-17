package lamp.admin.core.app.domain;

import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.core.base.exception.LampErrorCode;
import lamp.admin.utils.assembler.AbstractAssembler;
import lamp.admin.utils.assembler.Populater;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AppInstallCommandAssembler extends AbstractAssembler<AppInstallCommandCreateForm, AppInstallCommand> implements Populater<AppInstallCommandUpdateForm, AppInstallCommand> {

	@Override protected AppInstallCommand doAssemble(AppInstallCommandCreateForm appInstallCommandCreateForm) {
		AppInstallCommand appInstallCommand;
		AppInstallCommandType InstallCommandsitory = appInstallCommandCreateForm.getType();
		if (AppInstallCommandType.EXECUTE.equals(InstallCommandsitory)) {
			appInstallCommand = new AppInstallExecuteCommand();
		} else if (AppInstallCommandType.CREATE_FILE.equals(InstallCommandsitory)) {
			appInstallCommand = new AppInstallFileCreateCommand();
		} else if (AppInstallCommandType.REMOVE_FILE.equals(InstallCommandsitory)) {
			appInstallCommand = new AppInstallFileRemoveCommand();
		} else {
			throw Exceptions.newException(LampErrorCode.UNSUPPORTED_APP_INSTALL_COMMAND_TYPE, InstallCommandsitory);
		}
		BeanUtils.copyProperties(appInstallCommandCreateForm, appInstallCommand);
		return appInstallCommand;
	}

	@Override public void populate(AppInstallCommandUpdateForm source, AppInstallCommand target) {
		BeanUtils.copyProperties(source, target);
	}

}
