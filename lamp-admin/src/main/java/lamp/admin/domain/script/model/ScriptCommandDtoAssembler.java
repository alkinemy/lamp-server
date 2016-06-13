package lamp.admin.domain.script.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lamp.admin.core.script.ScriptCommand;
import lamp.admin.core.script.ScriptExecuteCommand;
import lamp.admin.core.script.ScriptFileCreateCommand;
import lamp.admin.core.script.ScriptFileRemoveCommand;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.common.utils.assembler.AbstractListAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScriptCommandDtoAssembler extends AbstractListAssembler<ScriptCommandEntity, ScriptCommand> {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override protected ScriptCommand doAssemble(ScriptCommandEntity appInstallCommand) {
		ScriptCommand appInstallCommandDto;
		if (appInstallCommand instanceof ExecuteCommandEntity) {
			appInstallCommandDto = new ScriptExecuteCommand();
		} else if (appInstallCommand instanceof FileCreateCommandEntity) {
			appInstallCommandDto = new ScriptFileCreateCommand();
		} else if (appInstallCommand instanceof FileRemoveCommandEntity) {
			appInstallCommandDto = new ScriptFileRemoveCommand();
		} else {
			throw Exceptions.newException(LampErrorCode.UNSUPPORTED_SCRIPT_COMMAND_TYPE);
		}
		BeanUtils.copyProperties(appInstallCommand, appInstallCommandDto);
		return appInstallCommandDto;
	}


	public String stringify(List<ScriptCommandEntity> scriptCommandEntities) {
		if (scriptCommandEntities == null) {
			return null;
		}

		List<ScriptCommand> scriptCommandList = assemble(scriptCommandEntities);
		try {
			return objectMapper.writeValueAsString(scriptCommandList);
		} catch (JsonProcessingException e) {
			throw Exceptions.newException(LampErrorCode.INVALID_SCRIPT_COMMANDS, e);
		}
	}

}
