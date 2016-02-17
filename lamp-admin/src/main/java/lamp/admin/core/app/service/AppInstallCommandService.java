package lamp.admin.core.app.service;

import lamp.admin.core.app.domain.*;
import lamp.admin.core.app.repository.AppInstallCommandRepository;
import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.core.base.exception.LampErrorCode;
import lamp.admin.utils.assembler.SmartAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AppInstallCommandService {

	@Autowired
	private AppInstallCommandRepository appInstallCommandRepository;
	
	@Autowired
	private SmartAssembler smartAssembler;
	
	@Autowired
	private AppInstallScriptService appInstallScriptService;

	public List<AppInstallCommandDto> getAppInstallCommandDtoList() {
		List<AppInstallCommand> page = appInstallCommandRepository.findAll();
		return smartAssembler.assemble(page, AppInstallCommandDto.class);
	}

	public Page<AppInstallCommandDto> getAppInstallCommandDtoList(Pageable pageable) {
		Page<AppInstallCommand> page = appInstallCommandRepository.findAll(pageable);
		return smartAssembler.assemble(pageable, page, AppInstallCommandDto.class);
	}

	public AppInstallCommandDto getAppInstallCommandDto(Long id) {
		AppInstallCommand appInstallCommand = getAppInstallCommand(id);
		return smartAssembler.assemble(appInstallCommand, AppInstallCommandDto.class);
	}

	public AppInstallCommandDto getAppInstallCommandDtoOptional(Long id) {
		Optional<AppInstallCommand> appInstallCommandOptional = getAppInstallCommandOptional(id);
		return smartAssembler.assemble(appInstallCommandOptional.orElse(null), AppInstallCommandDto.class);
	}

	public Optional<AppInstallCommand> getAppInstallCommandOptional(Long id) {
		return Optional.ofNullable(appInstallCommandRepository.findOne(id));
	}

	public AppInstallCommand getAppInstallCommand(Long id) {
		Optional<AppInstallCommand> appInstallCommandOptional = getAppInstallCommandOptional(id);
		return appInstallCommandOptional.orElseThrow(() -> Exceptions.newException(LampErrorCode.APP_INSTALL_COMMAND_NOT_FOUND, id));
	}

	@Transactional
	public AppInstallCommand insertAppInstallCommand(Long scriptId, AppInstallCommandCreateForm editForm) {
		AppInstallCommand appInstallCommand = smartAssembler.assemble(editForm, AppInstallCommandCreateForm.class, AppInstallCommand.class);
		appInstallCommand.setAppInstallScript(appInstallScriptService.getAppInstallScript(scriptId));
		return appInstallCommandRepository.save(appInstallCommand);
	}

	public AppInstallCommandUpdateForm getAppInstallCommandUpdateForm(Long scriptId, Long commandId) {
		AppInstallCommand appInstallCommand = getAppInstallCommand(commandId);
		return smartAssembler.assemble(appInstallCommand, AppInstallCommand.class, AppInstallCommandUpdateForm.class);
	}

	@Transactional
	public AppInstallCommand updateAppInstallCommand(Long templateId, Long scriptId, Long commandId, AppInstallCommandUpdateForm editForm) {
		AppInstallCommand appInstallCommand = getAppInstallCommand(commandId);
		smartAssembler.populate(editForm, appInstallCommand, AppInstallCommandUpdateForm.class, AppInstallCommand.class);
		return appInstallCommand;
	}

	@Transactional
	public void deleteAppInstallCommand(Long templateId, Long scriptId, Long commandId) {
		appInstallCommandRepository.delete(commandId);
	}


}
