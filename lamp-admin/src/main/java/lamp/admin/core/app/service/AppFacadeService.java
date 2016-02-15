package lamp.admin.core.app.service;

import lamp.admin.core.agent.domain.Agent;
import lamp.admin.core.agent.service.AgentService;
import lamp.admin.core.app.domain.*;
import lamp.admin.core.support.agent.model.AgentAppRegisterForm;
import lamp.admin.core.support.agent.model.AgentAppUpdateFileForm;
import lamp.admin.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AppFacadeService {

	@Autowired
	private AppService appService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private AppTemplateService appTemplateService;
	@Autowired
	private ManagedAppService managedAppService;
	@Autowired
	private SmartAssembler smartAssembler;

	public List<AppDto> getAppDtoList(String agentId) {
		return appService.getAppList(agentId);
	}

	public AppDto getAppDto(String agentId, String appId) {
		return appService.getApp(agentId, appId);
	}

	public Page<ManagedAppDto> getManagedAppDtoList(Pageable pageable) {
		Page<ManagedApp> page = managedAppService.getManagedAppList(pageable);
		return smartAssembler.assemble(pageable, page, ManagedAppDto.class);
	}

	public ManagedAppDto getManagedAppDto(String id) {
		ManagedApp managedApp = managedAppService.getManagedApp(id);
		return smartAssembler.assemble(managedApp, ManagedAppDto.class);
	}

	public Optional<ManagedAppDto> getManagedAppDtoOptional(String id) {
		Optional<ManagedApp> managedAppOptional = managedAppService.getManagedAppOptional(id);
		ManagedAppDto managedAppDto = smartAssembler.assemble(managedAppOptional.orElse(null), ManagedAppDto.class);
		return Optional.ofNullable(managedAppDto);
	}

	public ManagedApp getManagedApp(String id) {
		return managedAppService.getManagedApp(id);
	}

	@Transactional
	public void registerApp(String agentId, AppRegisterForm form) {
		Agent agent = getAgent(agentId);

		Long templateId = form.getTemplateId();
		AppTemplate appTemplate = appTemplateService.getAppTemplate(templateId);

		AgentAppRegisterForm agentAppRegisterForm = appService.registerApp(agent, appTemplate, form);

		ManagedApp managedApp = new ManagedApp();
		managedApp.setId(agentAppRegisterForm.getId());
		managedApp.setName(agentAppRegisterForm.getName());
		managedApp.setDescription(agentAppRegisterForm.getDescription());
		managedApp.setTargetServer(agent.getTargetServer());
		managedApp.setAppTemplate(appTemplate);
		managedApp.setGroupId(agentAppRegisterForm.getGroupId());
		managedApp.setArtifactId(agentAppRegisterForm.getArtifactId());
		managedApp.setArtifactName(agentAppRegisterForm.getArtifactName());
		managedApp.setVersion(agentAppRegisterForm.getVersion());
		managedApp.setUpdatable(!agentAppRegisterForm.isPreInstalled());
		managedApp.setRegisterDate(LocalDateTime.now());
		managedApp.setInstallDate(managedApp.getRegisterDate());
		managedAppService.insert(managedApp);
	}

	@Transactional
	public void updateAppFile(String agentId, String appId, AppUpdateFileForm editForm) {
		Agent agent = getAgent(agentId);
		updateAppFile(agent, appId, editForm);
	}

	@Transactional
	public void updateAppFile(String appId, AppUpdateFileForm editForm) {
		Agent agent = getAgentByAppId(appId);
		updateAppFile(agent, appId, editForm);
	}

	@Transactional
	public void updateAppFile(Agent agent, String appId, AppUpdateFileForm editForm) {
		ManagedApp managedApp = managedAppService.getManagedApp(appId);

		AgentAppUpdateFileForm agentAppUpdateFileForm = appService.updateAppFile(agent, managedApp, editForm);

		//		managedApp.setAppTemplate(appTemplate);
		managedApp.setGroupId(agentAppUpdateFileForm.getGroupId());
		managedApp.setArtifactId(agentAppUpdateFileForm.getArtifactId());
		managedApp.setArtifactName(agentAppUpdateFileForm.getArtifactName());
		managedApp.setVersion(agentAppUpdateFileForm.getVersion());
		managedApp.setInstallDate(LocalDateTime.now());
	}



	@Transactional
	public void deregisterApp(String agentId, String appId, AppDeregisterForm form) {
		Agent agent = getAgent(agentId);
		appService.deregisterApp(agent, appId);

		managedAppService.delete(appId);
	}

	@Transactional
	public void deregisterApp(String appId, AppDeregisterForm form) {
		Agent agent = getAgentByAppId(appId);
		appService.deregisterApp(agent, appId);

		managedAppService.delete(appId);
	}

	public void startApp(String agentId, String appId, AppStartForm form) {
		Agent agent = getAgent(agentId);
		appService.startApp(agent, appId);
	}

	public void startApp(String appId, AppStartForm form) {
		Agent agent = getAgentByAppId(appId);
		appService.startApp(agent, appId);
	}

	public void stopApp(String agentId, String appId, AppStopForm form) {
		Agent agent = getAgent(agentId);
		appService.stopApp(agent, appId);
	}

	public void stopApp(String appId, AppStopForm form) {
		Agent agent = getAgentByAppId(appId);
		appService.stopApp(agent, appId);
	}

	protected Agent getAgent(String agentId) {
		return agentService.getAgent(agentId);
	}

	protected Agent getAgentByAppId(String appId) {
		ManagedApp managedApp = managedAppService.getManagedApp(appId);
		return agentService.getAgentByTargetServerId(managedApp.getTargetServer().getId());
	}


}
