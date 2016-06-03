package lamp.admin.domain.script.service;

import lamp.admin.domain.script.model.*;
import lamp.admin.domain.script.repository.ScriptCommandRepository;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.common.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ScriptCommandService {

	@Autowired
	private ScriptCommandRepository scriptCommandRepository;
	
	@Autowired
	private SmartAssembler smartAssembler;

	@Autowired
	private ScriptCommandsParser scriptCommandsParser;


	public List<ScriptCommandDto> getScriptCommandDtoList(String commandsJson) {
		List<ScriptCommand> page = scriptCommandsParser.parse(commandsJson);
		return smartAssembler.assemble(page, ScriptCommand.class, ScriptCommandDto.class);
	}

	public List<ScriptCommandDto> getScriptCommandDtoList() {
		List<ScriptCommand> page = scriptCommandRepository.findAll();
		return smartAssembler.assemble(page, ScriptCommand.class, ScriptCommandDto.class);
	}

	public Page<ScriptCommandDto> getScriptCommandDtoList(Pageable pageable) {
		Page<ScriptCommand> page = scriptCommandRepository.findAll(pageable);
		return smartAssembler.assemble(pageable, page, ScriptCommand.class, ScriptCommandDto.class);
	}

	public ScriptCommandDto getScriptCommandDto(Long id) {
		ScriptCommand scriptCommandCommand = getScriptCommand(id);
		return smartAssembler.assemble(scriptCommandCommand, ScriptCommand.class, ScriptCommandDto.class);
	}

	public ScriptCommandDto getScriptCommandDtoOptional(Long id) {
		Optional<ScriptCommand> scriptCommandCommandOptional = getScriptCommandOptional(id);
		return smartAssembler.assemble(scriptCommandCommandOptional.orElse(null), ScriptCommand.class, ScriptCommandDto.class);
	}

	public Optional<ScriptCommand> getScriptCommandOptional(Long id) {
		return Optional.ofNullable(scriptCommandRepository.findOne(id));
	}

	public ScriptCommand getScriptCommand(Long id) {
		Optional<ScriptCommand> scriptCommandCommandOptional = getScriptCommandOptional(id);
		return scriptCommandCommandOptional.orElseThrow(() -> Exceptions.newException(LampErrorCode.APP_INSTALL_COMMAND_NOT_FOUND, id));
	}

	@Transactional
	public void deleteScriptCommand(Long scriptId, Long commandId) {
		scriptCommandRepository.delete(commandId);
	}


}
