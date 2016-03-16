package lamp.admin.web.app.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.core.agent.service.AgentService;
import lamp.admin.core.app.AppManagementListener;
import lamp.admin.core.app.domain.*;
import lamp.admin.core.app.service.AppFacadeService;
import lamp.admin.core.app.service.AppManagementListenerService;
import lamp.admin.core.app.service.AppTemplateService;
import lamp.admin.core.base.exception.MessageException;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.FlashMessage;
import lamp.admin.web.support.annotation.MenuMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@MenuMapping(MenuConstants.MANAGED_APP)
@Controller
@RequestMapping("/app")
public class ManagedAppController {

	@Autowired
	private AgentService agentService;

	@Autowired
	private AppFacadeService appFacadeService;

	@Autowired
	private AppTemplateService appTemplateService;

	@Autowired
	private AppManagementListenerService appManagementListenerService;


	@RequestMapping(path = "", method = RequestMethod.GET)
	public String list(Pageable pageable, Model model) {
		Page<ManagedAppDto> page = appFacadeService.getManagedAppDtoList(pageable);
		model.addAttribute("page", page);
		return "app/list";
	}


	@RequestMapping(path = "/create", method = RequestMethod.GET)
	public String create(@ModelAttribute("editForm") ManagedAppRegisterForm editForm,
						 Model model) {

		ManagedAppRegisterForm registerForm = appFacadeService.getManagedAppRegisterForm(editForm.getAgentId(), editForm.getId());
		BeanUtils.copyProperties(registerForm, editForm);

		return createForm(editForm, model);
	}

	protected String createForm(ManagedAppRegisterForm editForm, Model model) {

		List<AppTemplateDto> appTemplateDtoList = appTemplateService.getAppTemplateDtoListByProcessTypeAndGroupIdAndArtifactId(editForm.getProcessType(), editForm.getGroupId(), editForm.getArtifactId());
		model.addAttribute("appTemplateList", appTemplateDtoList);

		return "app/edit";
	}

	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute("editForm") ManagedAppRegisterForm editForm,
						 Model model,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {
		try {
			appFacadeService.registerManagedApp(editForm);
			return "redirect:/app";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return createForm(editForm, model);
		}
	}

	@RequestMapping(path = "/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable("id") String id,
						 @ModelAttribute("editForm") AppDeregisterForm editForm,
						 Model model) {

		ManagedAppDto managedAppDto = appFacadeService.getManagedAppDto(id);
		model.addAttribute("managedApp", managedAppDto);

		editForm.setAppManagementListener(managedAppDto.getAppManagementListener());

		return deleteForm(id, editForm, model);
	}

	protected String deleteForm(String id, AppDeregisterForm editForm, Model model) {
		if (!model.containsAttribute("managedApp")) {
			ManagedAppDto managedAppDto = appFacadeService.getManagedAppDto(id);
			model.addAttribute("managedApp", managedAppDto);
		}
		List<AppManagementListener> appManagementListeners = appManagementListenerService.getAppManagementListenerList();
		model.addAttribute("appManagementListeners", appManagementListeners);

		return "app/delete";
	}

	@RequestMapping(path = "/{id}/delete", method = RequestMethod.POST)
	public String delete(@PathVariable("id") String id,
						 @ModelAttribute("editForm") AppDeregisterForm editForm,
						 Model model,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {
		try {
			appFacadeService.deregisterApp(id, editForm);
			return "redirect:/agent/{agentId}/app";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return deleteForm(id, editForm, model);
		}
	}


	@RequestMapping(path = "/{id}/start", method = RequestMethod.GET)
	public String start(@PathVariable("id") String id,
						 @ModelAttribute("editForm") AppStartForm editForm,
						 Model model) {

		ManagedAppDto managedAppDto = appFacadeService.getManagedAppDto(id);
		model.addAttribute("managedApp", managedAppDto);

		editForm.setAppManagementListener(managedAppDto.getAppManagementListener());

		return startForm(id, editForm, model);
	}

	protected String startForm(String id, AppStartForm editForm, Model model) {
		if (!model.containsAttribute("managedApp")) {
			ManagedAppDto managedAppDto = appFacadeService.getManagedAppDto(id);
			model.addAttribute("managedApp", managedAppDto);
		}

		List<AppManagementListener> appManagementListeners = appManagementListenerService.getAppManagementListenerList();
		model.addAttribute("appManagementListeners", appManagementListeners);

		return "app/start";
	}

	@RequestMapping(path = "/{id}/start", method = RequestMethod.POST)
	public String start(@PathVariable("id") String id,
						 @ModelAttribute("editForm") AppStartForm editForm,
						 Model model,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {
		try {
			appFacadeService.startApp(id, editForm);
			return "redirect:/app";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return startForm(id, editForm, model);
		}
	}
	

	@RequestMapping(path = "/{id}/stop", method = RequestMethod.GET)
	public String stop(@PathVariable("id") String id,
						 @ModelAttribute("editForm") AppStopForm editForm,
						 Model model) {

		ManagedAppDto managedAppDto = appFacadeService.getManagedAppDto(id);
		model.addAttribute("managedApp", managedAppDto);

		editForm.setAppManagementListener(managedAppDto.getAppManagementListener());

		return stopForm(id, editForm, model);
	}

	protected String stopForm(String id, AppStopForm editForm, Model model) {
		if (!model.containsAttribute("managedApp")) {
			ManagedAppDto managedAppDto = appFacadeService.getManagedAppDto(id);
			model.addAttribute("managedApp", managedAppDto);
		}

		List<AppManagementListener> appManagementListeners = appManagementListenerService.getAppManagementListenerList();
		model.addAttribute("appManagementListeners", appManagementListeners);

		return "app/stop";
	}

	@RequestMapping(path = "/{id}/stop", method = RequestMethod.POST)
	public String stop(@PathVariable("id") String id,
						 @ModelAttribute("editForm") AppStopForm editForm,
						 Model model,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {
		try {
			appFacadeService.stopApp(id, editForm);
			return "redirect:/app";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return stopForm(id, editForm, model);
		}
	}

	@RequestMapping(path = "/{id}/file", method = RequestMethod.GET)
	public String update(@PathVariable("id") String id,
						 @ModelAttribute("editForm") AppUpdateFileForm editForm, Model model) {

		ManagedAppDto managedAppDto = appFacadeService.getManagedAppDto(id);
		model.addAttribute("managedApp", managedAppDto);

		return "app/file";
	}

	@RequestMapping(path = "/{id}/file", method = RequestMethod.POST)
	public String update(@PathVariable("id") String id,
						 @ModelAttribute("editForm") AppUpdateFileForm editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return update(id, editForm, model);
		}

		try {
			appFacadeService.updateAppFile(id, editForm);

			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.UPDATE_SUCCESS));
			return "redirect:/app";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return update(id, editForm, model);
		}
	}




}
