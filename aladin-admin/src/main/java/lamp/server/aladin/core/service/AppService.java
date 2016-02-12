package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.Agent;
import lamp.server.aladin.core.domain.AppResource;
import lamp.server.aladin.core.domain.AppTemplate;
import lamp.server.aladin.core.dto.AppDto;
import lamp.server.aladin.core.dto.AppRegisterForm;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.core.exception.LampErrorCode;
import lamp.server.aladin.core.support.agent.AgentAppRegisterForm;
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
		Agent agent = agentService.getAgent(agentId).orElseThrow(() -> Exceptions.newException(LampErrorCode.AGENT_NOT_FOUND, agentId));
		return agentClient.getAppList(agent);
	}

	public AgentAppRegisterForm registerApp(String agentId, AppRegisterForm editForm) {
		Agent agent = agentService.getAgent(agentId).orElseThrow(() -> Exceptions.newException(LampErrorCode.AGENT_NOT_FOUND, agentId));
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

	public void startApp(String agentId, String appId) {
		Agent agent = agentService.getAgent(agentId).orElseThrow(() -> Exceptions.newException(LampErrorCode.AGENT_NOT_FOUND, agentId));

		agentClient.start(agent, appId);
	}

	public void stopApp(String agentId, String appId) {
		Agent agent = agentService.getAgent(agentId).orElseThrow(() -> Exceptions.newException(LampErrorCode.AGENT_NOT_FOUND, agentId));

		agentClient.stop(agent, appId);
	}

	public void deregisterApp(String agentId, String appId) {
		Agent agent = agentService.getAgent(agentId).orElseThrow(() -> Exceptions.newException(LampErrorCode.AGENT_NOT_FOUND, agentId));

		agentClient.deregister(agent, appId);
	}


}
