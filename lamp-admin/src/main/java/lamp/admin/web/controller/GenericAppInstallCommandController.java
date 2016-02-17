package lamp.admin.web.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.core.app.domain.AppInstallCommandCreateForm;
import lamp.admin.core.app.domain.AppInstallCommandUpdateForm;
import lamp.admin.core.app.domain.AppInstallScriptDto;
import lamp.admin.core.app.domain.AppTemplateDto;
import lamp.admin.core.app.service.AppInstallCommandService;
import lamp.admin.core.app.service.AppInstallScriptService;
import lamp.admin.core.app.service.AppTemplateService;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.support.FlashMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
public abstract class GenericAppInstallCommandController<CF extends AppInstallCommandCreateForm, UF extends AppInstallCommandUpdateForm> {

	@Autowired
	private AppTemplateService appTemplateService;
	@Autowired
	private AppInstallScriptService appInstallScriptService;
	@Autowired
	private AppInstallCommandService appInstallCommandService;


	protected abstract String getCreateViewName();

	@RequestMapping(path = "/create", method = RequestMethod.GET)
	public String createForm(@PathVariable("templateId") Long templateId,
							 @PathVariable("scriptId") Long scriptId,
							 @ModelAttribute("editForm") CF editForm, Model model) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_CREATE);

		AppTemplateDto appTemplateDto = appTemplateService.getAppTemplateDto(templateId);
		model.addAttribute("appTemplate", appTemplateDto);

		AppInstallScriptDto appInstallScriptDto = appInstallScriptService.getAppInstallScriptDto(scriptId);
		model.addAttribute("appInstallScriptDto", appInstallScriptDto);

		return getCreateViewName();
	}

	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public String create(@PathVariable("templateId") Long templateId,
						@PathVariable("scriptId") Long scriptId,
						@Valid @ModelAttribute("editForm") CF editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return createForm(templateId, scriptId, editForm, model);
		}

		appInstallCommandService.insertAppInstallCommand(scriptId, editForm);
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));


		return "redirect:/app/template/{templateId}/script/{scriptId}";
	}


	@RequestMapping(path = "/{commandId}/update", method = RequestMethod.GET)
	public String update(@PathVariable("templateId") Long templateId,
						@PathVariable("scriptId") Long scriptId,
						@PathVariable("commandId") Long commandId,
						@ModelAttribute("editForm") UF editForm, Model model) {
		AppInstallCommandUpdateForm updateForm = appInstallCommandService.getAppInstallCommandUpdateForm(scriptId, commandId);
		BeanUtils.copyProperties(updateForm, editForm);
		return updateForm(templateId, scriptId, commandId, editForm, model);
	}

	protected String updateForm(Long templateId, Long scriptId, Long commandId,
							UF editForm, Model model) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_UPDATE);
		return getCreateViewName();
	}

	@RequestMapping(path = "/{commandId}/update", method = RequestMethod.POST)
	public String update(@PathVariable("templateId") Long templateId,
						@PathVariable("scriptId") Long scriptId,
						@PathVariable("commandId") Long commandId,
						@Valid @ModelAttribute("editForm") UF editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return updateForm(templateId, scriptId, commandId, editForm, model);
		}
		appInstallCommandService.updateAppInstallCommand(templateId, scriptId, commandId, editForm);
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.UPDATE_SUCCESS));


		return "redirect:/app/template/{templateId}/script/{scriptId}";
	}

	@RequestMapping(path = "/{commandId}/delete", method = RequestMethod.GET)
	public String update(@PathVariable("templateId") Long templateId,
			@PathVariable("scriptId") Long scriptId,
			@PathVariable("commandId") Long commandId, Model model, RedirectAttributes redirectAttributes) {

		appInstallCommandService.deleteAppInstallCommand(templateId, scriptId, commandId);

		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.UPDATE_SUCCESS));

		return "redirect:/app/template/{templateId}/script/{scriptId}";
	}
}
