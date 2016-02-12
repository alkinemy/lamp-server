package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.Agent;
import lamp.server.aladin.core.domain.AppTemplate;
import lamp.server.aladin.core.domain.ManagedApp;
import lamp.server.aladin.core.dto.AppDto;
import lamp.server.aladin.core.dto.AppRegisterForm;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.core.exception.LampErrorCode;
import lamp.server.aladin.core.support.agent.AgentAppRegisterForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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

	public List<AppDto> getAppList(String agentId) {
		return appService.getAppList(agentId);
	}

	@Transactional
	public void registerApp(String agentId, AppRegisterForm editForm) {
		Agent agent = agentService.getAgent(agentId).orElseThrow(() -> Exceptions.newException(LampErrorCode.AGENT_NOT_FOUND, agentId));

		Long templateId = editForm.getTemplateId();
		AppTemplate appTemplate = appTemplateService.getAppTemplateOptional(templateId).orElseThrow(() -> Exceptions.newException(LampErrorCode.APP_TEMPLATE_NOT_FOUND, templateId));

		AgentAppRegisterForm agentAppRegisterForm = appService.registerApp(agent, appTemplate, editForm);

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
		managedApp.setRegisterDate(LocalDateTime.now());

		managedAppService.insert(managedApp);
	}

	public void deregisterApp(String agentId, String appId) {
		appService.deregisterApp(agentId, appId);

		managedAppService.delete(agentId);
	}


	public void startApp(String agentId, String appId) {
		appService.startApp(agentId, appId);
	}

	public void stopApp(String agentId, String appId) {
		appService.stopApp(agentId, appId);
	}

}
