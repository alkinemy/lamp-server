package lamp.admin.core.script.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.core.base.exception.LampErrorCode;
import lamp.admin.utils.assembler.AbstractListAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScriptCommandDtoAssembler extends AbstractListAssembler<ScriptCommand, ScriptCommandDto> {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override protected ScriptCommandDto doAssemble(ScriptCommand appInstallCommand) {
		ScriptCommandDto appInstallCommandDto;
		if (appInstallCommand instanceof ExecuteCommand) {
			appInstallCommandDto = new ScriptExecuteCommandDto();
		} else if (appInstallCommand instanceof FileCreateCommand) {
			appInstallCommandDto = new ScriptFileCreateCommandDto();
		} else if (appInstallCommand instanceof FileRemoveCommand) {
			appInstallCommandDto = new ScriptFileRemoveCommandDto();
		} else {
			throw Exceptions.newException(LampErrorCode.UNSUPPORTED_SCRIPT_COMMAND_TYPE);
		}
		BeanUtils.copyProperties(appInstallCommand, appInstallCommandDto);
		return appInstallCommandDto;
	}


	public String stringify(List<ScriptCommand> scriptCommands) {
		if (scriptCommands == null) {
			return null;
		}

		List<ScriptCommandDto> scriptCommandDtoList = assemble(scriptCommands);
		try {
			return objectMapper.writeValueAsString(scriptCommandDtoList);
		} catch (JsonProcessingException e) {
			throw Exceptions.newException(LampErrorCode.INVALID_SCRIPT_COMMANDS, e);
		}
	}

}
