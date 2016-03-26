package lamp.admin.domain.app.service;

import lamp.admin.domain.app.model.AppInstallScript;
import lamp.admin.domain.app.model.AppInstallScriptCreateForm;
import lamp.admin.domain.app.model.AppInstallScriptDto;
import lamp.admin.domain.app.model.AppInstallScriptUpdateForm;
import lamp.admin.domain.app.repository.AppInstallScriptRepository;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.common.utils.assembler.SmartAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AppInstallScriptService {

	@Autowired
	private AppInstallScriptRepository appInstallScriptRepository;

	@Autowired
	private SmartAssembler smartAssembler;

	public List<AppInstallScriptDto> getAppInstallScriptDtoList(String templateId) {
		List<AppInstallScript> page = appInstallScriptRepository.findAllByTemplateId(templateId);
		return smartAssembler.assemble(page, AppInstallScript.class, AppInstallScriptDto.class);
	}

	public Page<AppInstallScriptDto> getAppInstallScriptDtoList(String templateId, Pageable pageable) {
		Page<AppInstallScript> page = appInstallScriptRepository.findAllByTemplateId(templateId, pageable);
		return smartAssembler.assemble(pageable, page, AppInstallScript.class, AppInstallScriptDto.class);
	}

	public AppInstallScriptDto getAppInstallScriptDto(Long id) {
		AppInstallScript AppInstallScript = getAppInstallScript(id);
		return smartAssembler.assemble(AppInstallScript, AppInstallScript.class, AppInstallScriptDto.class);
	}

	public AppInstallScriptDto getAppInstallScriptDtoOptional(Long id) {
		Optional<AppInstallScript> AppInstallScriptOptional = getAppInstallScriptOptional(id);
		return smartAssembler.assemble(AppInstallScriptOptional.orElse(null), AppInstallScriptDto.class);
	}

	public Optional<AppInstallScript> getAppInstallScriptOptional(Long id) {
		return Optional.ofNullable(appInstallScriptRepository.findOne(id));
	}

	public AppInstallScript getAppInstallScript(Long id) {
		Optional<AppInstallScript> AppInstallScriptOptional = getAppInstallScriptOptional(id);
		return AppInstallScriptOptional.orElseThrow(() -> Exceptions.newException(LampErrorCode.APP_TEMPLATE_NOT_FOUND, id));
	}

	@Transactional
	public AppInstallScript insertAppInstallScript(String templateId, AppInstallScriptCreateForm editForm) {
		AppInstallScript appRepo = smartAssembler.assemble(editForm, AppInstallScriptCreateForm.class, AppInstallScript.class);
		appRepo.setTemplateId(templateId);
		return appInstallScriptRepository.save(appRepo);
	}

	@Transactional
	public AppInstallScript updateAppInstallScript(Long id, AppInstallScriptUpdateForm editForm) {
		AppInstallScript appInstallScript = getAppInstallScript(id);
		smartAssembler.populate(editForm, appInstallScript);
		return appInstallScript;
	}

	@Transactional
	public void deleteAppInstallScript(Long id) {
		appInstallScriptRepository.delete(id);
	}

	public AppInstallScriptUpdateForm getAppInstallScriptUpdateForm(Long id) {
		AppInstallScript appInstallScript = getAppInstallScript(id);
		return smartAssembler.assemble(appInstallScript, AppInstallScript.class, AppInstallScriptUpdateForm.class);
	}

}
