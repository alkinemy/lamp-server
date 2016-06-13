package lamp.admin.domain.script.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.common.utils.BooleanUtils;
import lamp.common.utils.StringUtils;
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

	public List<ScriptCommandEntity> parse(String commandsStr) {
		log.info("commandsStr = {}", commandsStr);
		List<ScriptCommandEntity> scriptCommandEntities = new ArrayList<>();
		if (StringUtils.isNotBlank(commandsStr)) {
			try {
				log.info("commands = {}", commandsStr);
				Map<String, Object>[] commandMaps = objectMapper.readValue(commandsStr, Map[].class);
				for (Map<String, Object> command : commandMaps) {
					ScriptCommandType type = ScriptCommandType.valueOf((String) command.get("type"));
					ScriptCommandEntity scriptCommandEntity;
					switch (type) {
						case EXECUTE:
							scriptCommandEntity = new ExecuteCommandEntity();
							((ExecuteCommandEntity) scriptCommandEntity).setCommandShell((String) command.get("commandShell"));
							((ExecuteCommandEntity) scriptCommandEntity).setCommandLine((String) command.get("commandLine"));
							break;
						case FILE_CREATE:
							scriptCommandEntity = new FileCreateCommandEntity();
							((FileCreateCommandEntity) scriptCommandEntity).setFilename((String) (command.get("filename")));
							((FileCreateCommandEntity) scriptCommandEntity).setContent((String) command.get("content"));
//							((ScriptFileCreateCommand)scriptCommand).setReadable(BooleanUtils.toBoolean(command.get("readable")));
//							((ScriptFileCreateCommand)scriptCommand).setWritable(BooleanUtils.toBoolean(command.get("writable")));
							((FileCreateCommandEntity) scriptCommandEntity).setExecutable(BooleanUtils.toBoolean(String.valueOf(command.get("executable"))));
							break;
						case FILE_REMOVE:
							scriptCommandEntity = new FileRemoveCommandEntity();
							((FileRemoveCommandEntity) scriptCommandEntity).setFilename((String) command.get("filename"));
							break;
						default:
							throw Exceptions.newException(LampErrorCode.UNSUPPORTED_SCRIPT_COMMAND_TYPE, type);
					}
					scriptCommandEntity.setName((String) command.get("name"));
					scriptCommandEntity.setDescription((String) command.get("description"));
					scriptCommandEntities.add(scriptCommandEntity);
				}
			} catch (Exception e) {
				log.warn("INVALID_SCRIPT_COMMANDS (" + commandsStr + ")", e);
				throw Exceptions.newException(LampErrorCode.INVALID_SCRIPT_COMMANDS, e);
			}
		}
		return scriptCommandEntities;
	}
}
