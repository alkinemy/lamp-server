package lamp.admin.domain.old.app.service;

import org.springframework.stereotype.Service;

@Service
public class AppTemplateService {

//	@Autowired
//	private AppTemplateRepository appTemplateRepository;
//
//	@Autowired
//	private SmartAssembler smartAssembler;
//
//	public List<AppTemplateDto> getAppTemplateDtoList() {
//		List<AppTemplate> page = appTemplateRepository.findAll();
//		return smartAssembler.assemble(page, AppTemplateDto.class);
//	}
//
//	public Page<AppTemplateDto> getAppTemplateDtoList(Pageable pageable) {
//		Page<AppTemplate> page = appTemplateRepository.findAll(pageable);
//		return smartAssembler.assemble(pageable, page, AppTemplateDto.class);
//	}
//
//	public AppTemplateDto getAppTemplateDto(String id) {
//		AppTemplate appTemplate = getAppTemplate(id);
//		return smartAssembler.assemble(appTemplate, AppTemplateDto.class);
//	}
//
//	public AppTemplateDto getAppTemplateDtoOptional(String id) {
//		Optional<AppTemplate> appTemplateOptional = getAppTemplateOptional(id);
//		return smartAssembler.assemble(appTemplateOptional.orElse(null), AppTemplateDto.class);
//	}
//
//	public Optional<AppTemplate> getAppTemplateOptional(String id) {
//		return Optional.ofNullable(appTemplateRepository.findOne(id));
//	}
//
//	public AppTemplate getAppTemplate(String id) {
//		Optional<AppTemplate> appTemplateOptional = getAppTemplateOptional(id);
//		return appTemplateOptional.orElseThrow(() -> Exceptions.newException(LampErrorCode.APP_TEMPLATE_NOT_FOUND, id));
//	}
//
//	@Transactional
//	public AppTemplate insertAppTemplate(AppTemplateCreateForm editForm) {
//		AppTemplate appRepo = smartAssembler.assemble(editForm, AppTemplateCreateForm.class, AppTemplate.class);
//		return appTemplateRepository.save(appRepo);
//	}
//
//	@Transactional
//	public AppTemplate updateAppTemplate(String id, AppTemplateUpdateForm editForm) {
//		AppTemplate appTemplate = getAppTemplate(id);
//		smartAssembler.populate(editForm, appTemplate);
//		return appTemplate;
//	}
//
//	@Transactional
//	public void deleteAppTemplate(String id) {
//		appTemplateRepository.delete(id);
//	}
//
//	public AppTemplateUpdateForm getAppTemplateUpdateForm(String id) {
//		AppTemplate appTemplate = getAppTemplate(id);
//		AppTemplateUpdateForm form = new AppTemplateUpdateForm();
//		BeanUtils.copyProperties(appTemplate, form);
//		return form;
//	}
//
//	public Iterable<AppTemplate> getAppTemplateIterableByProcessTypeAndGroupIdAndArtifactId(String processType, String groupId, String artifactId) {
//		BooleanBuilder predicate = new BooleanBuilder();
//		QAppTemplate qAppTemplate = QAppTemplate.appTemplate;
//		if (StringUtils.isNotBlank(processType)) {
//			AppProcessType appProcessType = AppProcessType.valueOf(processType);
//			predicate.and(qAppTemplate.processType.eq(appProcessType));
//		}
//		if (StringUtils.isNotBlank(groupId)) {
//			predicate.and(qAppTemplate.groupId.eq(groupId));
//		}
//		if (StringUtils.isNotBlank(artifactId)) {
//			predicate.and(qAppTemplate.artifactId.eq(artifactId));
//		}
//		return appTemplateRepository.findAll(predicate);
//	}
//
//	public List<AppTemplateDto> getAppTemplateDtoListByProcessTypeAndGroupIdAndArtifactId(String processType, String groupId, String artifactId) {
//		Iterable<AppTemplate> appTemplateIterable = getAppTemplateIterableByProcessTypeAndGroupIdAndArtifactId(processType, groupId, artifactId);
//		List<AppTemplate> appTemplateList = Lists.newArrayList(appTemplateIterable);
//		return smartAssembler.assemble(appTemplateList, AppTemplateDto.class);
//	}

}
