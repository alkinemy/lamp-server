package lamp.admin.web.server.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.domain.agent.model.AgentDto;
import lamp.admin.domain.agent.service.AgentService;
import lamp.admin.domain.app.AppManagementListener;
import lamp.admin.domain.app.model.*;
import lamp.admin.domain.app.service.*;
import lamp.admin.domain.base.exception.MessageException;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.FlashMessage;
import lamp.admin.web.support.annotation.MenuMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@MenuMapping(MenuConstants.AGENT_APP)
@Controller
@RequestMapping("/server/agent/{agentId}")
public class AgentAppController {

	@Autowired
	private AgentService agentService;

	@Autowired
	private AppFacadeService appFacadeService;

	@Autowired
	private AppTemplateService appTemplateService;

	@Autowired
	private AppRepoService appRepoService;

	@Autowired
	private AppInstallScriptService appInstallScriptService;
	
	@Autowired
	private AppManagementListenerService appManagementListenerService;


	@RequestMapping(path = "/app", method = RequestMethod.GET)
	public String list(@PathVariable("agentId") String agentId, Model model) {
		List<AppDto> appList = appFacadeService.getAppDtoList(agentId);
		model.addAttribute("appList", appList);
		return "server/agent/app/list";
	}

	@RequestMapping(path = "/app/create", method = RequestMethod.GET, params = {"step=step1"})
	public String createStep1(@PathVariable("agentId") String agentId,
			@ModelAttribute("editForm") AppRegisterForm editForm,
			Model model) {
		model.addAttribute("action", LampAdminConstants.ACTION_CREATE);

		// TODO Popup이나 Wizard 형식으로 분리하기
		List<AppTemplateDto> appTemplateList = appTemplateService.getAppTemplateDtoList();
		model.addAttribute("appTemplateList", appTemplateList);

		return "server/agent/app/edit-step1";
	}

	@RequestMapping(path = "/app/create", method = RequestMethod.POST, params = {"step=step1"})
	public String createStep1(@PathVariable("agentId") String agentId,
			@Valid @ModelAttribute("editForm") AppRegisterForm editForm,
			Model model,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return createStep1(agentId, editForm, model);
		}

		return create(agentId, editForm, model, HttpMethod.GET);
	}

	@RequestMapping(path = "/app/create", method = RequestMethod.GET)
	public String create(@PathVariable("agentId") String agentId,
							 @ModelAttribute("editForm") AppRegisterForm editForm,
							 Model model, HttpMethod httpMethod) {
		model.addAttribute("action", LampAdminConstants.ACTION_CREATE);

		AppTemplateDto appTemplateDto = appTemplateService.getAppTemplateDtoOptional(editForm.getTemplateId());
		if (appTemplateDto == null) {
			return createStep1(agentId, editForm, model);
		}

		List<AppInstallScriptDto> appInstallScriptDtoList = appInstallScriptService.getAppInstallScriptDtoList(appTemplateDto.getId());
		model.addAttribute("appInstallScripts", appInstallScriptDtoList);

		model.addAttribute("appTemplate", appTemplateDto);
		model.addAttribute("parametersTypes", ParametersType.values());

		List<String> versions = appRepoService.getVersions(appTemplateDto.getRepositoryId(), appTemplateDto.getGroupId(), appTemplateDto.getArtifactId());
		model.addAttribute("versions", versions);

		if (httpMethod.equals(HttpMethod.GET)) {
			editForm.setId(appTemplateDto.getArtifactId());
			editForm.setName(appTemplateDto.getName());

			editForm.setParametersType(appTemplateDto.getParametersType());
			editForm.setParameters(appTemplateDto.getParameters());
			editForm.setVersion(versions.stream().findFirst().orElse(null));
		}
		return "server/agent/app/edit";
	}

	@RequestMapping(path = "/app/create", method = RequestMethod.POST)
	public String create(@PathVariable("agentId") String agentId,
						 @Valid @ModelAttribute("editForm") AppRegisterForm editForm,
						 Model model,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return create(agentId, editForm, model, HttpMethod.POST);
		}

		try {
			appFacadeService.registerApp(agentId, editForm);

			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));

			return "redirect:/server/agent/{agentId}/app";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return create(agentId, editForm, model, HttpMethod.POST);
		}

	}

	@RequestMapping(path = "/app/{appId}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable("agentId") String agentId,
						@PathVariable("appId") String appId,
						@ModelAttribute("editForm") AppDeregisterForm editForm,
						Model model) {

		Optional<ManagedAppDto> managedAppDtoOptional = appFacadeService.getManagedAppDtoOptional(appId);
		managedAppDtoOptional.ifPresent(managedAppDto -> editForm.setAppManagementListener(managedAppDto.getAppManagementListener()));

		return deleteForm(agentId, appId, editForm, model);
	}

	protected String deleteForm(String agentId, String appId, AppDeregisterForm editForm, Model model) {
		AppDto appDto = appFacadeService.getAppDto(agentId, appId);
		model.addAttribute("app", appDto);

		List<AppManagementListener> appManagementListeners = appManagementListenerService.getAppManagementListenerList();
		model.addAttribute("appManagementListeners", appManagementListeners);

		return "server/agent/app/delete";
	}

	@RequestMapping(path = "/app/{appId}/delete", method = RequestMethod.POST)
	public String delete(@PathVariable("agentId") String agentId,
						@PathVariable("appId") String appId,
						@ModelAttribute("editForm") AppDeregisterForm editForm,
						Model model,
						BindingResult bindingResult,
						RedirectAttributes redirectAttributes) {
		try {
			appFacadeService.deregisterApp(agentId, appId, editForm);
			return "redirect:/server/agent/{agentId}/app";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return deleteForm(agentId, appId, editForm, model);
		}
	}


	@RequestMapping(path = "/app/{appId}/start", method = RequestMethod.GET)
	public String start(@PathVariable("agentId") String agentId,
						@PathVariable("appId") String appId,
						@ModelAttribute("editForm") AppStartForm editForm,
						Model model) {

		Optional<ManagedAppDto> managedAppDtoOptional = appFacadeService.getManagedAppDtoOptional(appId);
		managedAppDtoOptional.ifPresent(managedAppDto -> editForm.setAppManagementListener(managedAppDto.getAppManagementListener()));

		return startForm(agentId, appId, editForm, model);
	}

	protected String startForm(String agentId, String appId, AppStartForm editForm, Model model) {
		AppDto appDto = appFacadeService.getAppDto(agentId, appId);
		model.addAttribute("app", appDto);

		List<AppManagementListener> appManagementListeners = appManagementListenerService.getAppManagementListenerList();
		model.addAttribute("appManagementListeners", appManagementListeners);
		
		return "server/agent/app/start";
	}

	@RequestMapping(path = "/app/{appId}/start", method = RequestMethod.POST)
	public String start(@PathVariable("agentId") String agentId,
						@PathVariable("appId") String appId,
						@ModelAttribute("editForm") AppStartForm editForm,
						Model model,
						BindingResult bindingResult,
						RedirectAttributes redirectAttributes) {
		try {
			appFacadeService.startApp(agentId, appId, editForm);
			return "redirect:/server/agent/{agentId}/app";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return startForm(agentId, appId, editForm, model);
		}
	}

	@RequestMapping(path = "/app/{appId}/stop", method = RequestMethod.GET)
	public String stop(@PathVariable("agentId") String agentId,
					   @PathVariable("appId") String appId,
					   @ModelAttribute("editForm") AppStopForm editForm,
					   Model model) {

		Optional<ManagedAppDto> managedAppDtoOptional = appFacadeService.getManagedAppDtoOptional(appId);
		managedAppDtoOptional.ifPresent(managedAppDto -> editForm.setAppManagementListener(managedAppDto.getAppManagementListener()));

		return stopForm(agentId, appId, editForm, model);
	}

	protected String stopForm(String agentId, String appId, AppStopForm editForm, Model model) {
		AppDto appDto = appFacadeService.getAppDto(agentId, appId);
		model.addAttribute("app", appDto);
		List<AppManagementListener> appManagementListeners = appManagementListenerService.getAppManagementListenerList();
		model.addAttribute("appManagementListeners", appManagementListeners);

		return "server/agent/app/stop";
	}

	@RequestMapping(path = "/app/{appId}/stop", method = RequestMethod.POST)
	public String stop(@PathVariable("agentId") String agentId,
						@PathVariable("appId") String appId,
						@ModelAttribute("editForm") AppStopForm editForm,
						Model model,
						BindingResult bindingResult,
						RedirectAttributes redirectAttributes) {
		try {
			appFacadeService.stopApp(agentId, appId, editForm);
			return "redirect:/server/agent/{agentId}/app";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return stopForm(agentId, appId, editForm, model);
		}
	}

	@ModelAttribute("agent")
	protected AgentDto getAgent(@PathVariable("agentId") String agentId) {
		return agentService.getAgentDto(agentId);
	}

}
