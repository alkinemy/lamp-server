package lamp.admin.web.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.core.app.domain.AppRepoCreateForm;
import lamp.admin.core.app.domain.AppRepoUpdateForm;
import lamp.admin.core.app.service.AppRepoService;
import lamp.admin.core.base.exception.LampErrorCode;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.support.FlashMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
public abstract class GenericAppRepoController<CF extends AppRepoCreateForm, UF extends AppRepoUpdateForm> {

	@Autowired
	private AppRepoService appRepoService;

	protected abstract String getCreateViewName();

	@RequestMapping(path = "/create", method = RequestMethod.GET)
	public String createForm(@ModelAttribute("editForm") CF editForm, Model model) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_CREATE);
		return getCreateViewName();
	}

	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("editForm") CF editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		boolean duplicated = appRepoService.existAppRepoByName(editForm.getName());
		if (duplicated) {
			bindingResult.rejectValue("name", LampErrorCode.DUPLICATED_APP_REPO_NAME.name(), LampErrorCode.DUPLICATED_APP_REPO_NAME.getDefaultMessage());
		}

		if (bindingResult.hasErrors()) {
			return createForm(editForm, model);
		}

		appRepoService.insertAppRepo(editForm);
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));


		return "redirect:/app/repository";
	}


	@RequestMapping(path = "/update", method = RequestMethod.GET)
	public String update(@ModelAttribute("editForm") UF editForm, Model model) {
		AppRepoUpdateForm updateForm = appRepoService.getAppRepoUpdateForm(editForm.getId());
		BeanUtils.copyProperties(updateForm, editForm);
		return updateForm(editForm, model);
	}

	protected String updateForm(UF editForm, Model model) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_UPDATE);
		return getCreateViewName();
	}

	@RequestMapping(path = "/update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("editForm") UF editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return updateForm(editForm, model);
		}
		appRepoService.updateAppRepo(editForm);
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.UPDATE_SUCCESS));


		return "redirect:/app/repository";
	}

}
