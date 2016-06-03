package lamp.admin.domain.old.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppFacadeService {

//	@Autowired
//	private AppService appService;
//	@Autowired
//	private AgentService agentService;
//	@Autowired
//	private AppTemplateService appTemplateService;
//	@Autowired
//	private ManagedAppService managedAppService;
//	@Autowired
//	private SmartAssembler smartAssembler;
//
//	public List<AppDto> getAppDtoList(String agentId) {
//		return appService.getAppDtoList(agentId);
//	}
//
//	public AppDto getAppDto(String agentId, String appId) {
//		return appService.getAppDto(agentId, appId);
//	}
//
//	public Page<ManagedAppDto> getManagedAppDtoList(Pageable pageable) {
//		Page<ManagedApp> page = managedAppService.getManagedAppList(pageable);
//		return smartAssembler.assemble(pageable, page, ManagedAppDto.class);
//	}
//
//	public ManagedAppDto getManagedAppDto(String id) {
//		ManagedApp managedApp = managedAppService.getManagedApp(id);
//		return smartAssembler.assemble(managedApp, ManagedAppDto.class);
//	}
//
//	public Optional<ManagedAppDto> getManagedAppDtoOptional(String id) {
//		Optional<ManagedApp> managedAppOptional = managedAppService.getManagedAppOptional(id);
//		ManagedAppDto managedAppDto = smartAssembler.assemble(managedAppOptional.orElse(null), ManagedAppDto.class);
//		return Optional.ofNullable(managedAppDto);
//	}
//
//	public ManagedApp getManagedApp(String id) {
//		return managedAppService.getManagedApp(id);
//	}
//
//	public ManagedAppRegisterForm getManagedAppRegisterForm(String agentId, String appId) {
//		ManagedAppRegisterForm form = new ManagedAppRegisterForm();
//
//		AppDto appDto = appService.getAppDto(agentId, appId);
//		BeanUtils.copyProperties(appDto, form);
//		form.setAgentId(agentId);
//
//		Iterable<AppTemplate> appTemplateIterable = appTemplateService.getAppTemplateIterableByProcessTypeAndGroupIdAndArtifactId(appDto.getProcessType(), appDto.getGroupId(), appDto.getArtifactId());
//		AppTemplate appTemplate = Iterables.getFirst(appTemplateIterable, null);
//		if (appTemplate != null) {
//			form.setTemplateId(appTemplate.getId());
//		}
//		return form;
//	}
//
//
//	@Transactional
//	public void deployApp(AppTemplateDeployForm form) {
//		// TODO 개발 에러 발생시 처리 필요
//		for (int i = 0; i < form.getTargetServerIds().size() ; i++) {
//			AppDeployForm appDeployForm = new AppDeployForm();
//			BeanUtils.copyProperties(form, appDeployForm);
//
//			appDeployForm.setId(form.getIds()[i]);
//			deployApp(form.getTargetServerIds().get(i), appDeployForm);
//		}
//	}
//
//
//	@Transactional
//	public void deployApp(String agentId, AppDeployForm form) {
//		log.info("deployApp ({}, {})", agentId, form.getId());
//		if (StringUtils.isBlank(form.getId())) {
//			form.setId(UUID.randomUUID().toString());
//		}
//
//		Agent agent = getAgent(agentId);
//
//		String templateId = form.getTemplateId();
//		AppTemplate appTemplate = appTemplateService.getAppTemplate(templateId);
//
//		AgentAppRegisterForm agentAppRegisterForm = appService.deployApp(agent, appTemplate, form);
//
//		ManagedApp managedApp = new ManagedApp();
//		managedApp.setId(form.getId());
//		managedApp.setName(agentAppRegisterForm.getName());
//		managedApp.setDescription(agentAppRegisterForm.getDescription());
//		managedApp.setTargetServer(agent.getTargetServer());
//		managedApp.setAppTemplate(appTemplate);
//		managedApp.setGroupId(agentAppRegisterForm.getGroupId());
//		managedApp.setArtifactId(agentAppRegisterForm.getArtifactId());
//		managedApp.setArtifactName(agentAppRegisterForm.getArtifactName());
//		managedApp.setVersion(agentAppRegisterForm.getVersion());
//		managedApp.setUpdatable(!agentAppRegisterForm.isPreInstalled());
//		managedApp.setRegisterDate(LocalDateTime.now());
//		if (!agentAppRegisterForm.isPreInstalled()) {
//			managedApp.setInstallDate(managedApp.getRegisterDate());
//		}
//
//		managedAppService.insert(managedApp);
//	}
//
//	@Transactional
//	public void redeployApp(String agentId, AppRedeployForm form) {
//		Agent agent = getAgent(agentId);
//
//		String templateId = form.getTemplateId();
//		AppTemplate appTemplate = appTemplateService.getAppTemplate(templateId);
//
//		AgentAppRegisterForm agentAppRegisterForm = appService.redeployApp(agent, appTemplate, form);
//
//		ManagedApp managedApp = managedAppService.getManagedApp(form.getId());
//		managedApp.setAppTemplate(appTemplate);
//		managedApp.setGroupId(agentAppRegisterForm.getGroupId());
//		managedApp.setArtifactId(agentAppRegisterForm.getArtifactId());
//		managedApp.setArtifactName(agentAppRegisterForm.getArtifactName());
//		managedApp.setVersion(agentAppRegisterForm.getVersion());
//		managedApp.setVersion(form.getVersion());
//	}
//
//	@Transactional
//	public void registerManagedApp(ManagedAppRegisterForm editForm) {
//		Agent agent = getAgent(editForm.getAgentId());
//
//		String templateId = editForm.getTemplateId();
//		AppTemplate appTemplate = appTemplateService.getAppTemplate(templateId);
//
//		if (StringUtils.isBlank(editForm.getId())) {
//			editForm.setId(UUID.randomUUID().toString());
//		}
//		ManagedApp managedApp = new ManagedApp();
//		BeanUtils.copyProperties(editForm, managedApp);
//		managedApp.setTargetServer(agent.getTargetServer());
//		managedApp.setAppTemplate(appTemplate);
//		managedApp.setUpdatable(!appTemplate.isPreInstalled());
//		managedApp.setRegisterDate(LocalDateTime.now());
//
//		managedAppService.insert(managedApp);
//	}
//
//	@Transactional
//	public void updateAppFile(String agentId, String appId, AppUpdateFileForm editForm) {
//		Agent agent = getAgent(agentId);
//		updateAppFile(agent, appId, editForm);
//	}
//
//	@Transactional
//	public void updateAppFile(String appId, AppUpdateFileForm editForm) {
//		Agent agent = getAgentByAppId(appId);
//		updateAppFile(agent, appId, editForm);
//	}
//
//	@Transactional
//	public void updateAppFile(Agent agent, String appId, AppUpdateFileForm editForm) {
//		ManagedApp managedApp = managedAppService.getManagedApp(appId);
//
//		AgentAppFileUpdateForm agentAppFileUpdateForm = appService.updateAppFile(agent, managedApp, editForm);
//
//		managedApp.setVersion(agentAppFileUpdateForm.getVersion());
//		managedApp.setInstallDate(LocalDateTime.now());
//	}
//
//
//
//	@Transactional
//	public void deregisterApp(String agentId, String appId, AppUndeployForm form) {
//		Agent agent = getAgent(agentId);
//		appService.undeployApp(agent, appId, form.isForceStop());
//
//		managedAppService.getManagedAppOptional(appId).ifPresent(managedAppService::delete);
//	}
//
//	@Transactional
//	public void deregisterApp(String appId, AppUndeployForm form) {
//		Agent agent = getAgentByAppId(appId);
//		appService.undeployApp(agent, appId, form.isForceStop());
//
//		managedAppService.getManagedAppOptional(appId).ifPresent(managedAppService::delete);
//	}
//
//	public void startApp(String agentId, String appId, AppStartForm form) {
//		Agent agent = getAgent(agentId);
//		appService.startApp(agent, appId);
//	}
//
//	public void startApp(String appId, AppStartForm form) {
//		Agent agent = getAgentByAppId(appId);
//		appService.startApp(agent, appId);
//	}
//
//	public void stopApp(String agentId, String appId, AppStopForm form) {
//		Agent agent = getAgent(agentId);
//		appService.stopApp(agent, appId);
//	}
//
//	public void stopApp(String appId, AppStopForm form) {
//		Agent agent = getAgentByAppId(appId);
//		appService.stopApp(agent, appId);
//	}
//
//	protected Agent getAgent(String agentId) {
//		return agentService.getAgent(agentId);
//	}
//
//	protected Agent getAgentByAppId(String appId) {
//		ManagedApp managedApp = managedAppService.getManagedApp(appId);
//		return agentService.getAgentByTargetServerId(managedApp.getTargetServer().getId());
//	}
//
//	public List<LogFile> getLogFiles(String agentId, String appId) {
//		Agent agent = getAgent(agentId);
//		return appService.getLogFiles(agent, appId);
//	}
//
//	public void transferLogFile(String agentId, String appId, String filename, ServletOutputStream outputStream) {
//		Agent agent = getAgent(agentId);
//		appService.transferLogFile(agent, appId, filename, outputStream);
//	}
}
