package lamp.admin.web.app.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.core.app.domain.AppInstallScriptCreateForm;
import lamp.admin.core.app.domain.AppInstallScriptUpdateForm;
import lamp.admin.core.app.domain.AppTemplateDto;
import lamp.admin.core.app.domain.CommandShell;
import lamp.admin.core.app.service.AppInstallScriptService;
import lamp.admin.core.app.service.AppTemplateService;
import lamp.admin.core.script.service.ScriptCommandService;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.service.JsonService;
import lamp.admin.web.support.FlashMessage;
import lamp.admin.web.support.annotation.MenuMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@MenuMapping(MenuConstants.APP_TEMPLATE)
@Controller
@RequestMapping("/app/template/{templateId}/script")
public class AppInstallScriptController {

	@Autowired
	private AppTemplateService appTemplateService;

	@Autowired
	private AppInstallScriptService appInstallScriptService;

	@Autowired
	private ScriptCommandService scriptCommandService;

	@Autowired
	private JsonService jsonService;

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String list(Model model, Pageable pageable) {

//		Page<AppTemplateDto> page = appInstallScriptService.getAppInstallScriptDtoList()
//		model.addAttribute("page", page);
		return "app/template/script/list";
	}

	@RequestMapping(path = "/{scriptId}", method = RequestMethod.GET)
	public String view(@PathVariable("templateId") String templateId,
					   @PathVariable("scriptId") Long scriptId,
					   Model model) {
		AppTemplateDto appTemplateDto= appTemplateService.getAppTemplateDto(templateId);
		model.addAttribute("appTemplate", appTemplateDto);


		return "app/template/script/view";
	}

	@RequestMapping(path = "/create", method = RequestMethod.GET)
	public String create(@PathVariable("templateId") String templateId, @ModelAttribute("editForm") AppInstallScriptCreateForm editForm, Model model) {


		return createForm(templateId, editForm, model);
	}

	protected String createForm(String templateId, @ModelAttribute("editForm") AppInstallScriptCreateForm editForm, Model model) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_CREATE);

		model.addAttribute("commandShells", CommandShell.values());

		return "app/template/script/edit";
	}

	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public String create(@PathVariable("templateId") String templateId, @Valid @ModelAttribute("editForm") AppInstallScriptCreateForm editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return createForm(templateId, editForm, model);
		}
		appInstallScriptService.insertAppInstallScript(templateId, editForm);

		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));


		return "redirect:/app/template/{id}";
	}


	@RequestMapping(path = "/{scriptId}/update", method = RequestMethod.GET)
	public String update(@PathVariable("templateId") String templateId,
						 @PathVariable("scriptId") Long scriptId,
						 @ModelAttribute("editForm") AppInstallScriptUpdateForm editForm, Model model) {

		AppInstallScriptUpdateForm updateForm = appInstallScriptService.getAppInstallScriptUpdateForm(scriptId);
		BeanUtils.copyProperties(updateForm, editForm);

		return updateForm(templateId, scriptId, editForm, model);
	}

	protected String updateForm(String templateId, Long scriptId,
			AppInstallScriptUpdateForm editForm, Model model) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_UPDATE);

		model.addAttribute("commandShells", CommandShell.values());
		String commandsJson = editForm.getCommands();

		// TODO 수정바람
		model.addAttribute("commandList", scriptCommandService.getScriptCommandDtoList(commandsJson));

		return "app/template/script/edit";
	}

	@RequestMapping(path = "/{scriptId}/update", method = RequestMethod.POST)
	public String update(@PathVariable("templateId") String templateId,
						 @PathVariable("scriptId") Long scriptId,
						 @Valid @ModelAttribute("editForm") AppInstallScriptUpdateForm editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return updateForm(templateId, scriptId, editForm, model);
		}
		appInstallScriptService.updateAppInstallScript(scriptId, editForm);

		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.UPDATE_SUCCESS));

		return "redirect:/app/template/{id}";
	}

	@RequestMapping(path = "/{scriptId}/delete", method = RequestMethod.GET)
	public String delete(@RequestParam("templateId") String templateId,
			@PathVariable("scriptId") Long scriptId,
			RedirectAttributes redirectAttributes) {

		appInstallScriptService.deleteAppInstallScript(scriptId);

		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.DELETE_SUCCESS));

		return "redirect:/app/template/{id}";
	}

	@ModelAttribute("JSON")
	protected JsonService jsonService() {
		return jsonService;
	}
}
