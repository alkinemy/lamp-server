package lamp.admin.core.script.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lamp.admin.core.app.domain.CommandShell;
import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.core.base.exception.LampErrorCode;
import lamp.admin.core.script.domain.*;
import lamp.admin.utils.BooleanUtils;
import lamp.admin.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ScriptCommandsParser {

	private ObjectMapper objectMapper;

	public ScriptCommandsParser() {
		objectMapper = new ObjectMapper();
	}

	public List<ScriptCommand> parse(String commandsStr) {
		log.info("commandsStr = {}", commandsStr);
		List<ScriptCommand> scriptCommands = new ArrayList<>();
		if (StringUtils.isNotBlank(commandsStr)) {
			try {
				Map<String, Object>[] commandMaps = objectMapper.readValue(commandsStr, Map[].class);
				log.info("commands = {}", commandMaps);
				for (Map<String, Object> command : commandMaps) {
					ScriptCommandType type = ScriptCommandType.valueOf((String) command.get("type"));
					ScriptCommand scriptCommand;
					switch (type) {
						case EXECUTE:
							scriptCommand = new ScriptExecuteCommand();
							((ScriptExecuteCommand)scriptCommand).setCommandShell(CommandShell.valueOf((String) command.get("commandShell")));
							((ScriptExecuteCommand)scriptCommand).setCommandLine((String) command.get("commandLine"));
							break;
						case FILE_CREATE:
							scriptCommand = new ScriptFileCreateCommand();
							((ScriptFileCreateCommand)scriptCommand).setFilename((String) (command.get("filename")));
							((ScriptFileCreateCommand)scriptCommand).setContent((String) command.get("content"));
//							((ScriptFileCreateCommand)scriptCommand).setRead(BooleanUtils.toBoolean(command.get("read")));
//							((ScriptFileCreateCommand)scriptCommand).setWrite(BooleanUtils.toBoolean(command.get("write")));
							((ScriptFileCreateCommand)scriptCommand).setExecute(BooleanUtils.toBoolean(String.valueOf(command.get("execute"))));
							break;
						case FILE_REMOVE:
							scriptCommand = new ScriptFileRemoveCommand();
							((ScriptFileRemoveCommand)scriptCommand).setFilename((String) command.get("filename"));
							break;
						default:
							throw Exceptions.newException(LampErrorCode.UNSUPPORTED_SCRIPT_COMMAND_TYPE, type);
					}
					scriptCommand.setName((String) command.get("name"));
					scriptCommand.setDescription((String) command.get("description"));
					scriptCommands.add(scriptCommand);
				}
			} catch (Exception e) {
				log.warn("INVALID_SCRIPT_COMMANDS (" + commandsStr + ")", e);
				throw Exceptions.newException(LampErrorCode.INVALID_SCRIPT_COMMANDS, e);
			}
		}
		return scriptCommands;
	}
}
