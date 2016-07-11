package lamp.admin.web.monitoring.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.domain.alert.service.AlertActionService;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.support.FlashMessage;
import lamp.monitoring.core.alert.model.AlertAction;
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

public abstract class GenericAlertActionController<A extends AlertAction> {

	@Autowired
	private AlertActionService alertActionService;


	@RequestMapping(path = "/create", method = RequestMethod.GET)
	public String createForm(@ModelAttribute("editForm") A editForm, Model model) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_CREATE);

		return "monitoring/alert-actions/" + getType() + "/edit";
	}

	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("editForm") A editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return createForm(editForm, model);
		}
		alertActionService.createAlertAction(editForm);
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));


		return "redirect:/monitoring/alert-actions";
	}

	@RequestMapping(path = "/{id}/update", method = RequestMethod.GET)
	public String update(@PathVariable("id") String id, @ModelAttribute("editForm") A editForm, Model model) {
		A updateForm = (A) alertActionService.getAlertAction(id);
		BeanUtils.copyProperties(updateForm, editForm);

		return updateForm(id, editForm, model);
	}

	protected String updateForm(String id, A editForm, Model model) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_UPDATE);

		return "monitoring/alert-actions/" + getType() + "/edit";
	}

	@RequestMapping(path = "/{id}/update", method = RequestMethod.POST)
	public String update(@PathVariable("id") String id, @ModelAttribute("editForm") A editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return updateForm(id, editForm, model);
		}

		alertActionService.updateAlertAction(id, editForm);
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.UPDATE_SUCCESS));

		return "redirect:/monitoring/alert-actions";
	}

	protected abstract String getType();
}
