package lamp.admin.domain.script.service;

import lamp.admin.core.script.ScriptCommand;
import lamp.admin.domain.script.model.*;
import lamp.admin.domain.script.repository.ScriptCommandEntityRepository;
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
public class ScriptCommandEntityService {

	@Autowired
	private ScriptCommandEntityRepository scriptCommandEntityRepository;
	
	@Autowired
	private SmartAssembler smartAssembler;

	@Autowired
	private ScriptCommandsParser scriptCommandsParser;


	public List<ScriptCommand> getScriptCommandDtoList(String commandsJson) {
		List<ScriptCommandEntity> page = scriptCommandsParser.parse(commandsJson);
		return smartAssembler.assemble(page, ScriptCommandEntity.class, ScriptCommand.class);
	}

	public List<ScriptCommand> getScriptCommandDtoList() {
		List<ScriptCommandEntity> page = scriptCommandEntityRepository.findAll();
		return smartAssembler.assemble(page, ScriptCommandEntity.class, ScriptCommand.class);
	}

	public Page<ScriptCommand> getScriptCommandDtoList(Pageable pageable) {
		Page<ScriptCommandEntity> page = scriptCommandEntityRepository.findAll(pageable);
		return smartAssembler.assemble(pageable, page, ScriptCommandEntity.class, ScriptCommand.class);
	}

	public ScriptCommand getScriptCommandDto(Long id) {
		ScriptCommandEntity scriptCommandCommandEntity = getScriptCommand(id);
		return smartAssembler.assemble(scriptCommandCommandEntity, ScriptCommandEntity.class, ScriptCommand.class);
	}

	public ScriptCommand getScriptCommandDtoOptional(Long id) {
		Optional<ScriptCommandEntity> scriptCommandCommandOptional = getScriptCommandOptional(id);
		return smartAssembler.assemble(scriptCommandCommandOptional.orElse(null), ScriptCommandEntity.class, ScriptCommand.class);
	}

	public Optional<ScriptCommandEntity> getScriptCommandOptional(Long id) {
		return Optional.ofNullable(scriptCommandEntityRepository.findOne(id));
	}

	public ScriptCommandEntity getScriptCommand(Long id) {
		Optional<ScriptCommandEntity> scriptCommandCommandOptional = getScriptCommandOptional(id);
		return scriptCommandCommandOptional.orElseThrow(() -> Exceptions.newException(LampErrorCode.APP_INSTALL_COMMAND_NOT_FOUND, id));
	}

	@Transactional
	public void deleteScriptCommand(Long scriptId, Long commandId) {
		scriptCommandEntityRepository.delete(commandId);
	}


}
