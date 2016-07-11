package lamp.admin.web.monitoring.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.domain.alert.model.HostMetricsAlertRule;
import lamp.admin.domain.alert.service.AlertActionService;
import lamp.admin.domain.base.exception.FlashMessageException;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.monitoring.service.HostAlertRuleService;
import lamp.admin.web.support.FlashMessage;
import lamp.admin.web.support.annotation.MenuMapping;
import lamp.monitoring.core.alert.model.AlertAction;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@MenuMapping(MenuConstants.MONITORING_HOST_ALERT_RULES)
@Controller
@RequestMapping("/monitoring/hosts/alert-rules")
public class HostAlertRuleController {

	@Autowired
	private HostAlertRuleService hostAlertRuleService;

	@Autowired
	private AlertActionService alertActionService;


	@RequestMapping(path = "", method = RequestMethod.GET)
	public String list(Model model, @SortDefault(value = "name", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<HostMetricsAlertRule> page = hostAlertRuleService.getHostMetricsAlertRules(pageable);
		model.addAttribute("page", page);
		return "monitoring/hosts/alert-rules/list";
	}

	@RequestMapping(path = "/create", method = RequestMethod.GET)
	public String createForm(@ModelAttribute("editForm") HostMetricsAlertRule editForm, Model model) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_CREATE);

		return "monitoring/hosts/alert-rules/edit";
	}

	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("editForm") HostMetricsAlertRule editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return createForm(editForm, model);
		}
		hostAlertRuleService.insertHostMetricsAlertRule(editForm);
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));


		return "redirect:/monitoring/hosts/alert-rules";
	}

	@RequestMapping(path = "/{id}/update", method = RequestMethod.GET)
	public String update(@PathVariable("id") String id, @ModelAttribute("editForm") HostMetricsAlertRule editForm, Model model) {
		HostMetricsAlertRule updateForm = hostAlertRuleService.getHostMetricsAlertRule(id);
		BeanUtils.copyProperties(updateForm, editForm);

		return updateForm(id, editForm, model);
	}

	protected String updateForm(String id, HostMetricsAlertRule editForm, Model model) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_UPDATE);

		return "monitoring/hosts/alert-rules/edit";
	}

	@RequestMapping(path = "/{id}/update", method = RequestMethod.POST)
	public String update(@PathVariable("id") String id, @ModelAttribute("editForm") HostMetricsAlertRule editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return updateForm(id, editForm, model);
		}

		hostAlertRuleService.updateHostMetricsAlertRule(id, editForm);
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.UPDATE_SUCCESS));

		return "redirect:/monitoring/hosts/alert-rules";
	}

	@RequestMapping(path = "/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable("id") String id
			, RedirectAttributes redirectAttributes) {

		FlashMessage flashMessage;
		try {
			hostAlertRuleService.deleteHostMetricsAlertRule(id);
			flashMessage = FlashMessage.ofSuccess(AdminErrorCode.DELETE_SUCCESS);
		} catch (FlashMessageException e) {
			flashMessage = FlashMessage.ofError(e.getMessage(), e.getCode(), e.getArgs());
		}
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, flashMessage);

		return "redirect:/monitoring/hosts/alert-rules";
	}


	@ModelAttribute("alertActions")
	public List<AlertAction> getAlertActions() {
		return alertActionService.getPubilcAlertActions();
	}
}
