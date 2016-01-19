package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.AppTemplate;
import lamp.server.aladin.core.dto.AppTemplateCreateForm;
import lamp.server.aladin.core.dto.AppTemplateDto;
import lamp.server.aladin.core.repository.AppTemplateRepository;
import lamp.server.aladin.utils.assembler.SmartAssembler;
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

	public List<AppTemplateDto> getAppTemplatesitoryList() {
		List<AppTemplate> page = appTemplateRepository.findAll();
		return smartAssembler.assemble(page, AppTemplateDto.class);
	}

	public Page<AppTemplateDto> getAppTemplatesitoryList(Pageable pageable) {
		Page<AppTemplate> page = appTemplateRepository.findAll(pageable);
		return smartAssembler.assemble(pageable, page, AppTemplateDto.class);
	}

	public Optional<AppTemplate> getAppTemplate(Long id) {
		return Optional.ofNullable(appTemplateRepository.findOne(id));
	}

	@Transactional
	public AppTemplate insertAppTemplate(AppTemplateCreateForm editForm) {
		AppTemplate appRepo = smartAssembler.assemble(editForm, AppTemplateCreateForm.class, AppTemplate.class);
		return appTemplateRepository.save(appRepo);
	}


}
