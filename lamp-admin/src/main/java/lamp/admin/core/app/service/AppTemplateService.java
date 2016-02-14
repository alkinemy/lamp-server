package lamp.admin.core.app.service;

import lamp.admin.core.app.domain.AppTemplate;
import lamp.admin.core.app.domain.AppTemplateCreateForm;
import lamp.admin.core.app.domain.AppTemplateDto;
import lamp.admin.core.app.domain.AppTemplateUpdateForm;
import lamp.admin.core.app.repository.AppTemplateRepository;
import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.core.base.exception.LampErrorCode;
import lamp.admin.utils.assembler.SmartAssembler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AppTemplateService {

	@Autowired
	private AppTemplateRepository appTemplateRepository;

	@Autowired
	private SmartAssembler smartAssembler;

	public List<AppTemplateDto> getAppTemplateDtoList() {
		List<AppTemplate> page = appTemplateRepository.findAll();
		return smartAssembler.assemble(page, AppTemplateDto.class);
	}

	public Page<AppTemplateDto> getAppTemplateDtoList(Pageable pageable) {
		Page<AppTemplate> page = appTemplateRepository.findAll(pageable);
		return smartAssembler.assemble(pageable, page, AppTemplateDto.class);
	}

	public Optional<AppTemplate> getAppTemplateOptional(Long id) {
		return Optional.ofNullable(appTemplateRepository.findOne(id));
	}

	public AppTemplate getAppTemplate(Long id) {
		Optional<AppTemplate> appTemplateOptional = getAppTemplateOptional(id);
		return appTemplateOptional.orElseThrow(() -> Exceptions.newException(LampErrorCode.APP_TEMPLATE_NOT_FOUND, id));
	}

	@Transactional
	public AppTemplate insertAppTemplate(AppTemplateCreateForm editForm) {
		AppTemplate appRepo = smartAssembler.assemble(editForm, AppTemplateCreateForm.class, AppTemplate.class);
		return appTemplateRepository.save(appRepo);
	}

	@Transactional
	public AppTemplate updateAppTemplate(AppTemplateUpdateForm editForm) {
		AppTemplate appTemplate = getAppTemplate(editForm.getId());
		smartAssembler.populate(editForm, appTemplate);
		return appTemplate;
	}

	@Transactional
	public void deleteAppTemplate(Long id) {
		appTemplateRepository.delete(id);
	}

	public AppTemplateUpdateForm getAppTemplateUpdateForm(Long id) {
		AppTemplate appTemplate = getAppTemplate(id);
		AppTemplateUpdateForm form = new AppTemplateUpdateForm();
		BeanUtils.copyProperties(appTemplate, form);
		return form;
	}


}
