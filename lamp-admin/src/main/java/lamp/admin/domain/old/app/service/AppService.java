package lamp.admin.domain.old.app.service;

import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.agent.service.AgentService;
import lamp.admin.domain.app.model.*;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lamp.admin.domain.old.app.model.*;
import lamp.admin.domain.script.model.ScriptCommandDtoAssembler;
import lamp.admin.domain.support.agent.AgentClient;
import lamp.admin.domain.support.agent.model.AgentAppRegisterForm;
import lamp.admin.domain.support.agent.model.AgentAppFileUpdateForm;
import lamp.admin.domain.support.agent.model.AgentAppUpdateSpecForm;
import lamp.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
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

	@Autowired
	private AppInstallScriptService appInstallScriptService;

	@Autowired
	private ScriptCommandDtoAssembler scriptCommandDtoAssembler;

	public List<AppDto> getAppDtoList(String agentId) {
		Agent agent = agentService.getAgent(agentId);
		return agentClient.getAppList(agent);
	}

	public AppDto getAppDto(String agentId, String appId) {
		Agent agent = agentService.getAgent(agentId);
		return agentClient.getApp(agent, appId);
	}

	public AgentAppRegisterForm deployApp(String agentId, AppDeployForm editForm) {
		Agent agent = agentService.getAgent(agentId);
		String templateId = editForm.getTemplateId();
		AppTemplate appTemplate = appTemplateService.getAppTemplateOptional(templateId).orElseThrow(() -> Exceptions.newException(LampErrorCode.APP_TEMPLATE_NOT_FOUND, templateId));
		return deployApp(agent, appTemplate, editForm);
	}

	public AgentAppRegisterForm deployApp(Agent agent, AppTemplate appTemplate, AppDeployForm editForm) {
		AgentAppRegisterForm agentAppRegisterForm = new AgentAppRegisterForm();
		agentAppRegisterForm.setId(editForm.getId());
		agentAppRegisterForm.setName(editForm.getName());

		agentAppRegisterForm.setGroupId(appTemplate.getGroupId());
		agentAppRegisterForm.setArtifactId(appTemplate.getArtifactId());
		agentAppRegisterForm.setArtifactName(appTemplate.getArtifactName());
		agentAppRegisterForm.setVersion(StringUtils.defaultIfBlank(editForm.getVersion(), appTemplate.getVersion()));
		agentAppRegisterForm.setProcessType(appTemplate.getProcessType());
		agentAppRegisterForm.setAppDirectory(appTemplate.getAppDirectory());
		agentAppRegisterForm.setWorkDirectory(appTemplate.getWorkDirectory());
		agentAppRegisterForm.setLogDirectory(appTemplate.getLogDirectory());
		agentAppRegisterForm.setPidFile(appTemplate.getPidFile());
		agentAppRegisterForm.setPtql(appTemplate.getPtql());
		agentAppRegisterForm.setStdOutFile(appTemplate.getStdOutFile());
		agentAppRegisterForm.setStdErrFile(appTemplate.getStdErrFile());
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

			String filename = appTemplate.getAppFilename();
			if (StringUtils.isBlank(filename)) {
				filename = appResource.getFilename();
			}
			agentAppRegisterForm.setFilename(filename);
		}

		agentAppRegisterForm.setMonitor(ObjectUtils.defaultIfNull(editForm.getMonitor(), Boolean.FALSE));

		Long installScriptId = editForm.getInstallScriptId();
		if (installScriptId != null) {
			AppInstallScript installScript = appInstallScriptService.getAppInstallScript(installScriptId);
			String commands = scriptCommandDtoAssembler.stringify(installScript.getCommands());
			agentAppRegisterForm.setCommands(commands);
		}

		agentAppRegisterForm.setParametersType(appTemplate.getParametersType());
		agentAppRegisterForm.setParameters(appTemplate.getParameters());

		agentClient.register(agent, agentAppRegisterForm);

		return agentAppRegisterForm;
	}

	public AgentAppRegisterForm redeployApp(Agent agent, AppTemplate appTemplate, AppRedeployForm appRedeployForm) {
		agentClient.deregister(agent, appRedeployForm.getId(), appRedeployForm.isForceStop());

		return deployApp(agent, appTemplate, appRedeployForm);
	}

	public AgentAppFileUpdateForm updateAppFile(Agent agent, ManagedApp managedApp, AppUpdateFileForm editForm) {
		AppTemplate appTemplate = managedApp.getAppTemplate();

		AgentAppFileUpdateForm agentAppFileUpdateForm = new AgentAppFileUpdateForm();
		agentAppFileUpdateForm.setId(managedApp.getId());
		agentAppFileUpdateForm.setVersion(StringUtils.defaultIfBlank(editForm.getVersion(), appTemplate.getVersion()));

		String groupId = managedApp.getGroupId();
		String artifactId = managedApp.getArtifactId();
		String version = agentAppFileUpdateForm.getVersion();
		AppResource appResource = appResourceService.getResource(appTemplate, groupId, artifactId, version);
		agentAppFileUpdateForm.setVersion(appResource.getVersion());
		agentAppFileUpdateForm.setInstallFile(appResource);

		agentClient.updateFile(agent, agentAppFileUpdateForm);
		return agentAppFileUpdateForm;
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

	public void undeployApp(Agent agent, String appId, boolean forceStop) {
		agentClient.deregister(agent, appId, forceStop);
	}

	public void startApp(Agent agent, String appId) {
		agentClient.start(agent, appId);
	}

	public void stopApp(Agent agent, String appId) {
		agentClient.stop(agent, appId);
	}

	public List<LogFile> getLogFiles(Agent agent, String appId) {
		return agentClient.getLogFiles(agent, appId);
	}

	public void transferLogFile(Agent agent, String appId, String filename, ServletOutputStream outputStream) {
		agentClient.transferLogFile(agent, appId, filename, outputStream);
	}

}
