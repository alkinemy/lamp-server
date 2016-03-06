package lamp.admin.web.controller;

import lamp.admin.web.MenuConstants;
import lamp.admin.web.service.UserRegisterService;
import lamp.admin.web.support.annotation.MenuMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@MenuMapping(MenuConstants.ACCOUNT_APPROVAL)
@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private UserRegisterService userRegisterService;

	@RequestMapping(path = "/approval", method = RequestMethod.GET)
	public String approval(Model model) {
		model.addAttribute("users", userRegisterService.getNotActivatedUsers());
		return "account/approval";
	}


}
