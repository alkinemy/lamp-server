package lamp.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/account")
public class AccountController {

	@RequestMapping(path = "/approval", method = RequestMethod.GET)
	public String approval() {
		return "account/approval";
	}
}
