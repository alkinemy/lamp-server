package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.Agent;
import lamp.server.aladin.core.domain.AppResource;
import lamp.server.aladin.core.domain.AppTemplate;
import lamp.server.aladin.core.domain.ManagedApp;
import lamp.server.aladin.core.dto.AppDto;
import lamp.server.aladin.core.dto.AppRegisterForm;
import lamp.server.aladin.core.dto.AppUpdateFileForm;
import lamp.server.aladin.core.dto.AppUpdateSpecForm;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.core.exception.LampErrorCode;
import lamp.server.aladin.core.support.agent.AgentAppRegisterForm;
import lamp.server.aladin.core.support.agent.AgentAppUpdateFileForm;
import lamp.server.aladin.core.support.agent.AgentAppUpdateSpecForm;
import lamp.server.aladin.core.support.agent.AgentClient;
import lamp.server.aladin.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AppService {

	@Autowired
	private AgentClient agentClient;

	@Autowired
	private AgentService agentService;

	@Autowired
	private AppTemplateService appTemplateService;

	@Autowired
	private AppResourceService appResourceService;


	public List<AppDto> getAppList(String agentId) {
		Agent agent = agentService.getAgentOptional(agentId).orElseThrow(() -> Exceptions.newException(LampErrorCode.AGENT_NOT_FOUND, agentId));
		return agentClient.getAppList(agent);
	}

	public AgentAppRegisterForm registerApp(String agentId, AppRegisterForm editForm) {
		Agent agent = agentService.getAgentOptional(agentId).orElseThrow(() -> Exceptions.newException(LampErrorCode.AGENT_NOT_FOUND, agentId));
		Long templateId = editForm.getTemplateId();
		AppTemplate appTemplate = appTemplateService.getAppTemplateOptional(templateId).orElseThrow(() -> Exceptions.newException(LampErrorCode.APP_TEMPLATE_NOT_FOUND, templateId));
		return registerApp(agent, appTemplate, editForm);
	}

	public AgentAppRegisterForm registerApp(Agent agent, AppTemplate appTemplate, AppRegisterForm editForm) {
		AgentAppRegisterForm agentAppRegisterForm = new AgentAppRegisterForm();
		agentAppRegisterForm.setId(editForm.getId());
		agentAppRegisterForm.setName(editForm.getName());
		agentAppRegisterForm.setGroupId(appTemplate.getGroupId());
		agentAppRegisterForm.setArtifactId(appTemplate.getArtifactId());
		agentAppRegisterForm.setArtifactName(appTemplate.getArtifactName());
		agentAppRegisterForm.setVersion(StringUtils.defaultIfBlank(editForm.getVersion(), appTemplate.getVersion()));
		agentAppRegisterForm.setProcessType(appTemplate.getProcessType());
		agentAppRegisterForm.setPidFile(appTemplate.getPidFile());
		agentAppRegisterForm.setAppDirectory(appTemplate.getAppDirectory());
		agentAppRegisterForm.setWorkDirectory(appTemplate.getWorkDirectory());
		agentAppRegisterForm.setStartCommandLine(appTemplate.getStartCommandLine());
		agentAppRegisterForm.setStopCommandLine(appTemplate.getStopCommandLine());

		agentAppRegisterForm.setPreInstalled(appTemplate.isPreInstalled());
		if (!appTemplate.isPreInstalled()) {
			String groupId = agentAppRegisterForm.getGroupId();
			String artifactId = agentAppRegisterForm.getArtifactId();
			String version = agentAppRegisterForm.getVersion();
			AppResource appResource = appResourceService.getResource(appTemplate, groupId, artifactId, version);
			agentAppRegisterForm.setVersion(appResource.getVersion());
			agentAppRegisterForm.setInstallFile(appResource);
		}
		agentAppRegisterForm.setFilename(appTemplate.getAppFilename());
		agentAppRegisterForm.setMonitor(ObjectUtils.defaultIfNull(editForm.getMonitor(), appTemplate.isMonitor()));
		agentAppRegisterForm.setCommands(appTemplate.getCommands());

		agentClient.register(agent, agentAppRegisterForm);

		return agentAppRegisterForm;
	}

	public AgentAppUpdateSpecForm updateAppSpec(Agent agent, AppTemplate appTemplate, AppUpdateSpecForm editForm) {
		AgentAppUpdateSpecForm agentAppUpdateSpecForm = new AgentAppUpdateSpecForm();
		agentAppUpdateSpecForm.setId(editForm.getId());
		agentAppUpdateSpecForm.setName(editForm.getName());
		agentAppUpdateSpecForm.setProcessType(appTemplate.getProcessType());
		agentAppUpdateSpecForm.setPidFile(appTemplate.getPidFile());
		agentAppUpdateSpecForm.setAppDirectory(appTemplate.getAppDirectory());
		agentAppUpdateSpecForm.setWorkDirectory(appTemplate.getWorkDirectory());
		agentAppUpdateSpecForm.setStartCommandLine(appTemplate.getStartCommandLine());
		agentAppUpdateSpecForm.setStopCommandLine(appTemplate.getStopCommandLine());

		agentClient.updateSpec(agent, agentAppUpdateSpecForm);
		return agentAppUpdateSpecForm;
	}

	public AgentAppUpdateFileForm updateAppFile(Agent agent, ManagedApp managedApp, AppUpdateFileForm editForm) {
		AppTemplate appTemplate = managedApp.getAppTemplate();

		AgentAppUpdateFileForm agentAppUpdateFileForm = new AgentAppUpdateFileForm();
		agentAppUpdateFileForm.setId(managedApp.getId());
		agentAppUpdateFileForm.setGroupId(appTemplate.getGroupId());
		agentAppUpdateFileForm.setArtifactId(appTemplate.getArtifactId());
		agentAppUpdateFileForm.setArtifactName(appTemplate.getArtifactName());
		agentAppUpdateFileForm.setVersion(StringUtils.defaultIfBlank(editForm.getVersion(), appTemplate.getVersion()));

		String groupId = agentAppUpdateFileForm.getGroupId();
		String artifactId = agentAppUpdateFileForm.getArtifactId();
		String version = agentAppUpdateFileForm.getVersion();
		AppResource appResource = appResourceService.getResource(appTemplate, groupId, artifactId, version);
		agentAppUpdateFileForm.setVersion(appResource.getVersion());
		agentAppUpdateFileForm.setInstallFile(appResource);

		agentClient.updateFile(agent, agentAppUpdateFileForm);
		return agentAppUpdateFileForm;
	}

	public void deregisterApp(Agent agent, String appId) {
		agentClient.deregister(agent, appId);
	}


	public void startApp(Agent agent, String appId) {
		agentClient.start(agent, appId);
	}

	public void stopApp(Agent agent, String appId) {
		agentClient.stop(agent, appId);
	}

}
