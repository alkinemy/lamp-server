package lamp.admin.core.app.domain;

import lamp.admin.core.script.domain.ScriptCommandDtoAssembler;
import lamp.common.utils.assembler.AbstractAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppInstallScriptUpdateFormAssembler extends AbstractAssembler<AppInstallScript, AppInstallScriptUpdateForm> {

	@Autowired
	private ScriptCommandDtoAssembler scriptCommandDtoAssembler;

	@Override protected AppInstallScriptUpdateForm doAssemble(AppInstallScript appInstallScript) {
		AppInstallScriptUpdateForm updateForm = new AppInstallScriptUpdateForm();
		BeanUtils.copyProperties(appInstallScript, updateForm, "commands");

		updateForm.setCommands(scriptCommandDtoAssembler.stringify(appInstallScript.getCommands()));

		return updateForm;
	}

}
