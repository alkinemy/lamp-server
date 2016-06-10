package lamp.admin.web.app.controller;

import lamp.admin.api.util.HttpServletRequestUtils;
import lamp.admin.domain.app.base.model.form.SimpleAppCreateForm;
import lamp.admin.domain.app.base.model.form.SpringBootAppCreateForm;
import lamp.admin.domain.app.base.service.AppService;
import lamp.admin.domain.base.exception.MessageException;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.annotation.MenuMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@MenuMapping(MenuConstants.APP)
@Controller
@RequestMapping("/apps")
public class SimpleAppController {

	@Autowired
	private AppService appService;
//
//	@Autowired
//	private AppFacadeService appFacadeService;
//
//	@Autowired
//	private AppTemplateService appTemplateService;
//
//	@Autowired
//	private AppManagementListenerService appManagementListenerService;
//


	@RequestMapping(path = "/**", method = RequestMethod.GET, params = {"action=create-simple-app"})
	public String create(HttpServletRequest request, Model model,
						@ModelAttribute("editForm") SimpleAppCreateForm editForm) {
		String path = HttpServletRequestUtils.getRestPath(request);

		log.error("PATH = {}", path);
		model.addAttribute("path", path);
		return createForm(model, editForm);
	}

	protected String createForm(Model model, SimpleAppCreateForm editForm) {

		return "apps/simple-app-edit";
	}

	@RequestMapping(path = "/**", method = RequestMethod.POST, params = {"action=create-simple-app"})
	public String create(HttpServletRequest request, Model model,
						 @ModelAttribute("editForm") SimpleAppCreateForm editForm,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {
		String path = HttpServletRequestUtils.getRestPath(request);
		try {
//			appService.createApp(path, editForm);
			return "redirect:/apps";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return createForm(model, editForm);
		}
	}
//
//	@RequestMapping(path = "/{id}/delete", method = RequestMethod.GET)
//	public String delete(@PathVariable("id") String id,
//						 @ModelAttribute("editForm") AppUndeployForm editForm,
//						 Model model) {
//
//		ManagedAppDto managedAppDto = appFacadeService.getManagedAppDto(id);
//		model.addAttribute("managedApp", managedAppDto);
//
//		editForm.setAppManagementListener(managedAppDto.getAppManagementListener());
//
//		return deleteForm(id, editForm, model);
//	}
//
//	protected String deleteForm(String id, AppUndeployForm editForm, Model model) {
//		if (!model.containsAttribute("managedApp")) {
//			ManagedAppDto managedAppDto = appFacadeService.getManagedAppDto(id);
//			model.addAttribute("managedApp", managedAppDto);
//		}
//		List<AppManagementListener> appManagementListeners = appManagementListenerService.getAppManagementListenerList();
//		model.addAttribute("appManagementListeners", appManagementListeners);
//
//		return "app/delete";
//	}
//
//	@RequestMapping(path = "/{id}/delete", method = RequestMethod.POST)
//	public String delete(@PathVariable("id") String id,
//						 @ModelAttribute("editForm") AppUndeployForm editForm,
//						 Model model,
//						 BindingResult bindingResult,
//						 RedirectAttributes redirectAttributes) {
//		try {
//			appFacadeService.deregisterApp(id, editForm);
//			return "redirect:/agent/{agentId}/app";
//		} catch (MessageException e) {
//			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
//			return deleteForm(id, editForm, model);
//		}
//	}
//
//
//	@RequestMapping(path = "/{id}/start", method = RequestMethod.GET)
//	public String start(@PathVariable("id") String id,
//						 @ModelAttribute("editForm") AppStartForm editForm,
//						 Model model) {
//
//		ManagedAppDto managedAppDto = appFacadeService.getManagedAppDto(id);
//		model.addAttribute("managedApp", managedAppDto);
//
//		editForm.setAppManagementListener(managedAppDto.getAppManagementListener());
//
//		return startForm(id, editForm, model);
//	}
//
//	protected String startForm(String id, AppStartForm editForm, Model model) {
//		if (!model.containsAttribute("managedApp")) {
//			ManagedAppDto managedAppDto = appFacadeService.getManagedAppDto(id);
//			model.addAttribute("managedApp", managedAppDto);
//		}
//
//		List<AppManagementListener> appManagementListeners = appManagementListenerService.getAppManagementListenerList();
//		model.addAttribute("appManagementListeners", appManagementListeners);
//
//		return "app/start";
//	}
//
//	@RequestMapping(path = "/{id}/start", method = RequestMethod.POST)
//	public String start(@PathVariable("id") String id,
//						 @ModelAttribute("editForm") AppStartForm editForm,
//						 Model model,
//						 BindingResult bindingResult,
//						 RedirectAttributes redirectAttributes) {
//		try {
//			appFacadeService.startApp(id, editForm);
//			return "redirect:/app";
//		} catch (MessageException e) {
//			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
//			return startForm(id, editForm, model);
//		}
//	}
//
//
//	@RequestMapping(path = "/{id}/stop", method = RequestMethod.GET)
//	public String stop(@PathVariable("id") String id,
//						 @ModelAttribute("editForm") AppStopForm editForm,
//						 Model model) {
//
//		ManagedAppDto managedAppDto = appFacadeService.getManagedAppDto(id);
//		model.addAttribute("managedApp", managedAppDto);
//
//		editForm.setAppManagementListener(managedAppDto.getAppManagementListener());
//
//		return stopForm(id, editForm, model);
//	}
//
//	protected String stopForm(String id, AppStopForm editForm, Model model) {
//		if (!model.containsAttribute("managedApp")) {
//			ManagedAppDto managedAppDto = appFacadeService.getManagedAppDto(id);
//			model.addAttribute("managedApp", managedAppDto);
//		}
//
//		List<AppManagementListener> appManagementListeners = appManagementListenerService.getAppManagementListenerList();
//		model.addAttribute("appManagementListeners", appManagementListeners);
//
//		return "app/stop";
//	}
//
//	@RequestMapping(path = "/{id}/stop", method = RequestMethod.POST)
//	public String stop(@PathVariable("id") String id,
//						 @ModelAttribute("editForm") AppStopForm editForm,
//						 Model model,
//						 BindingResult bindingResult,
//						 RedirectAttributes redirectAttributes) {
//		try {
//			appFacadeService.stopApp(id, editForm);
//			return "redirect:/app";
//		} catch (MessageException e) {
//			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
//			return stopForm(id, editForm, model);
//		}
//	}
//
//	@RequestMapping(path = "/{id}/file", method = RequestMethod.GET)
//	public String update(@PathVariable("id") String id,
//						 @ModelAttribute("editForm") AppUpdateFileForm editForm, Model model) {
//
//		ManagedAppDto managedAppDto = appFacadeService.getManagedAppDto(id);
//		model.addAttribute("managedApp", managedAppDto);
//
//		return "app/file";
//	}
//
//	@RequestMapping(path = "/{id}/file", method = RequestMethod.POST)
//	public String update(@PathVariable("id") String id,
//						 @ModelAttribute("editForm") AppUpdateFileForm editForm,
//			BindingResult bindingResult, Model model,
//			RedirectAttributes redirectAttributes) {
//		if (bindingResult.hasErrors()) {
//			return update(id, editForm, model);
//		}
//
//		try {
//			appFacadeService.updateAppFile(id, editForm);
//
//			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.UPDATE_SUCCESS));
//			return "redirect:/app";
//		} catch (MessageException e) {
//			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
//			return update(id, editForm, model);
//		}
//	}




}
