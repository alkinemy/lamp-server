package lamp.server.aladin.admin.controller;

import lamp.server.aladin.LampConstants;
import lamp.server.aladin.admin.AdminErrorCode;
import lamp.server.aladin.admin.MenuConstants;
import lamp.server.aladin.admin.support.FlashMessage;
import lamp.server.aladin.admin.support.annotation.MenuMapping;
import lamp.server.aladin.core.domain.ManagedApp;
import lamp.server.aladin.core.dto.*;
import lamp.server.aladin.core.exception.MessageException;
import lamp.server.aladin.core.service.AgentService;
import lamp.server.aladin.core.service.AppFacadeService;
import lamp.server.aladin.core.service.AppTemplateService;
import lombok.extern.slf4j.Slf4j;
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

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String list(Pageable pageable, Model model) {
		Page<ManagedAppDto> page = appFacadeService.getManagedAppDtoList(pageable);
		model.addAttribute("page", page);
		return "app/list";
	}

	@RequestMapping(path = "/{id}/start", method = RequestMethod.GET)
	public String start(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		appFacadeService.startApp(id);

		return "redirect:/app";
	}

	@RequestMapping(path = "/{id}/stop", method = RequestMethod.GET)
	public String stop(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		appFacadeService.stopApp(id);

		return "redirect:/app";
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

			redirectAttributes.addFlashAttribute(LampConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.UPDATE_SUCCESS));
			return "redirect:/app";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return update(id, editForm, model);
		}
	}


	@RequestMapping(path = "/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		appFacadeService.deregisterApp(id);

		return "redirect:/app";
	}

}
