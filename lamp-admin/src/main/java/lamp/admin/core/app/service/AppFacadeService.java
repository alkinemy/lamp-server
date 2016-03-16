package lamp.admin.core.app.service;

import com.google.common.collect.Iterables;
import lamp.admin.core.agent.domain.Agent;
import lamp.admin.core.agent.service.AgentService;
import lamp.admin.core.app.domain.*;
import lamp.admin.core.support.agent.model.AgentAppRegisterForm;
import lamp.admin.core.support.agent.model.AgentAppUpdateFileForm;
import lamp.common.utils.assembler.SmartAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
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
		return appService.getAppDtoList(agentId);
	}

	public AppDto getAppDto(String agentId, String appId) {
		return appService.getAppDto(agentId, appId);
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

	public ManagedAppRegisterForm getManagedAppRegisterForm(String agentId, String appId) {
		ManagedAppRegisterForm form = new ManagedAppRegisterForm();

		AppDto appDto = appService.getAppDto(agentId, appId);
		BeanUtils.copyProperties(appDto, form);
		form.setAgentId(agentId);

		Iterable<AppTemplate> appTemplateIterable = appTemplateService.getAppTemplateIterableByProcessTypeAndGroupIdAndArtifactId(appDto.getProcessType(), appDto.getGroupId(), appDto.getArtifactId());
		AppTemplate appTemplate = Iterables.getFirst(appTemplateIterable, null);
		if (appTemplate != null) {
			form.setTemplateId(appTemplate.getId());
		}
		return form;
	}

	@Transactional
	public void registerApp(String agentId, AppRegisterForm form) {
		Agent agent = getAgent(agentId);

		String templateId = form.getTemplateId();
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
		if (!agentAppRegisterForm.isPreInstalled()) {
			managedApp.setInstallDate(managedApp.getRegisterDate());
		}

		managedAppService.insert(managedApp);
	}

	@Transactional
	public void registerManagedApp(ManagedAppRegisterForm editForm) {
		Agent agent = getAgent(editForm.getAgentId());

		String templateId = editForm.getTemplateId();
		AppTemplate appTemplate = appTemplateService.getAppTemplate(templateId);

		ManagedApp managedApp = new ManagedApp();
		BeanUtils.copyProperties(editForm, managedApp);
		managedApp.setTargetServer(agent.getTargetServer());
		managedApp.setAppTemplate(appTemplate);
		managedApp.setUpdatable(!appTemplate.isPreInstalled());
		managedApp.setRegisterDate(LocalDateTime.now());

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

		managedAppService.getManagedAppOptional(appId).ifPresent(managedAppService::delete);
	}

	@Transactional
	public void deregisterApp(String appId, AppDeregisterForm form) {
		Agent agent = getAgentByAppId(appId);
		appService.deregisterApp(agent, appId);

		managedAppService.getManagedAppOptional(appId).ifPresent(managedAppService::delete);
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

	public List<LogFile> getLogFiles(String agentId, String appId) {
		Agent agent = getAgent(agentId);
		return appService.getLogFiles(agent, appId);
	}

	public void transferLogFile(String agentId, String appId, String filename, ServletOutputStream outputStream) {
		Agent agent = getAgent(agentId);
		appService.transferLogFile(agent, appId, filename, outputStream);
	}
}
