package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.Agent;
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
		return null;
	}

	public void registerApp(String agentId, AppRegisterForm editForm) {
		Agent agent = agentService.getAgent(agentId).orElseThrow(() -> Exceptions.newException(LampErrorCode.AGENT_NOT_FOUND, agentId));


		Long templateId = editForm.getTemplateId();
		AppTemplate appTemplate = appTemplateService.getAppTemplate(templateId).orElseThrow(() -> Exceptions.newException(LampErrorCode.APP_TEMPLATE_NOT_FOUND, templateId));

		AgentAppRegisterForm agentAppRegisterForm = new AgentAppRegisterForm();
		agentAppRegisterForm.setId((editForm.getId()));
		agentAppRegisterForm.setName(editForm.getName());
		agentAppRegisterForm.setAppId(appTemplate.getAppId());
		agentAppRegisterForm.setAppName(appTemplate.getAppName());
		agentAppRegisterForm.setAppVersion(StringUtils.defaultIfBlank(editForm.getAppVersion(), appTemplate.getAppVersion()));
		agentAppRegisterForm.setProcessType(appTemplate.getProcessType());
		agentAppRegisterForm.setPidFile(appTemplate.getPidFile());
		agentAppRegisterForm.setStartCommandLine(appTemplate.getStartCommandLine());
		agentAppRegisterForm.setStopCommandLine(appTemplate.getStopCommandLine());

		agentAppRegisterForm.setPreInstalled(appTemplate.isPreInstalled());
		if (!appTemplate.isPreInstalled()) {
			String groupId = appTemplate.getGroupId();
			String artifactId = agentAppRegisterForm.getAppId();
			String version = agentAppRegisterForm.getAppVersion();
			agentAppRegisterForm.setInstallFile(appResourceService.getResource(appTemplate, groupId, artifactId, version));
		}
		agentAppRegisterForm.setFilename(appTemplate.getAppFilename());
		agentAppRegisterForm.setMonitor(ObjectUtils.defaultIfNull(editForm.getMonitor(), appTemplate.isMonitor()));
		agentAppRegisterForm.setCommands(appTemplate.getCommands());

		agentClient.register(agent, agentAppRegisterForm);
	}

	public void delete(Agent agent) {

	}


}
