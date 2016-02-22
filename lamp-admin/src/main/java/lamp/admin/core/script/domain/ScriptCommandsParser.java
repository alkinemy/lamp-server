package lamp.admin.core.script.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.core.base.exception.LampErrorCode;
import lamp.admin.utils.BooleanUtils;
import lamp.admin.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
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
				log.info("commands = {}", commandsStr);
				Map<String, Object>[] commandMaps = objectMapper.readValue(commandsStr, Map[].class);
				for (Map<String, Object> command : commandMaps) {
					ScriptCommandType type = ScriptCommandType.valueOf((String) command.get("type"));
					ScriptCommand scriptCommand;
					switch (type) {
						case EXECUTE:
							scriptCommand = new ExecuteCommand();
							((ExecuteCommand)scriptCommand).setCommandShell((String) command.get("commandShell"));
							((ExecuteCommand)scriptCommand).setCommandLine((String) command.get("commandLine"));
							break;
						case FILE_CREATE:
							scriptCommand = new FileCreateCommand();
							((FileCreateCommand)scriptCommand).setFilename((String) (command.get("filename")));
							((FileCreateCommand)scriptCommand).setContent((String) command.get("content"));
//							((ScriptFileCreateCommand)scriptCommand).setReadable(BooleanUtils.toBoolean(command.get("readable")));
//							((ScriptFileCreateCommand)scriptCommand).setWritable(BooleanUtils.toBoolean(command.get("writable")));
							((FileCreateCommand)scriptCommand).setExecutable(BooleanUtils.toBoolean(String.valueOf(command.get("executable"))));
							break;
						case FILE_REMOVE:
							scriptCommand = new FileRemoveCommand();
							((FileRemoveCommand)scriptCommand).setFilename((String) command.get("filename"));
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
