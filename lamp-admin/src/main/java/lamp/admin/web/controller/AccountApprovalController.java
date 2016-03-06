package lamp.admin.web.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.service.UserRegisterService;
import lamp.admin.web.support.FlashMessage;
import lamp.admin.web.support.annotation.MenuMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@MenuMapping(MenuConstants.ACCOUNT_APPROVAL)
@Controller
@RequestMapping("/account/approval")
public class AccountApprovalController {

	@Autowired
	private UserRegisterService userRegisterService;

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String approval(Model model) {
		model.addAttribute("users", userRegisterService.getNotActivatedUsers());
		return "account/approval";
	}

	//TODO post로 처리?
	@RequestMapping(path = "/allow", method = RequestMethod.GET)
	public String allowAccount(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
		userRegisterService.allow(id);
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.UPDATE_SUCCESS));
		return "redirect:/account/approval";
	}

	//TODO post로 처리?
	@RequestMapping(path = "/deny", method = RequestMethod.GET)
	public String denyAccount(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
		//TODO 구현?
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.UPDATE_SUCCESS));
		return "redirect:/account/approval";
	}

}
