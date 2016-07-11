package lamp.admin.web.monitoring.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.domain.alert.service.AlertActionService;
import lamp.admin.domain.base.exception.FlashMessageException;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.FlashMessage;
import lamp.admin.web.support.annotation.MenuMapping;
import lamp.monitoring.core.alert.model.AlertAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@MenuMapping(MenuConstants.MONITORING_ALERT_ACTIONS)
@Controller
@RequestMapping("/monitoring/alert-actions")
public class AlertActionController {

	@Autowired
	private AlertActionService alertActionService;

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String list(Model model, @SortDefault(value = "name", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<AlertAction> page = alertActionService.getPublicAlertActions(pageable);
		model.addAttribute("page", page);
		return "monitoring/alert-actions/list";
	}

	@RequestMapping(path = "/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable("id") String id
			, RedirectAttributes redirectAttributes) {

		FlashMessage flashMessage;
		try {
			alertActionService.deleteAlertAction(id);
			flashMessage = FlashMessage.ofSuccess(AdminErrorCode.DELETE_SUCCESS);
		} catch (FlashMessageException e) {
			flashMessage = FlashMessage.ofError(e.getMessage(), e.getCode(), e.getArgs());
		}
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, flashMessage);

		return "redirect:/monitoring/alert-actions";
	}


}
