package lamp.admin.web.account.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.domain.base.exception.FlashMessageException;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.account.model.UserRegisterForm;
import lamp.admin.web.account.service.UserRegisterService;
import lamp.admin.web.support.FlashMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@Controller
public class LoginController {

	@Autowired
	private UserRegisterService userRegisterService;

	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String login() {
		return "auth/auth";
	}

	@RequestMapping(path = "/signup", method = RequestMethod.GET)
	public String signUp() {
		return "auth/signup";
	}

	@RequestMapping(path = "/forget", method = RequestMethod.GET)
	public String forget() {
		return "auth/forget";
	}

	@RequestMapping(path = "/signup", method = RequestMethod.POST)
	public String register(@ModelAttribute @Valid UserRegisterForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		//TODO binding result 처리
		FlashMessage flashMessage;
		try {
			userRegisterService.register(form);
			flashMessage = FlashMessage.ofSuccess(AdminErrorCode.USER_REGISTER_SUCCESS);
		} catch (FlashMessageException e) {
			flashMessage = FlashMessage.ofError(e.getMessage(), e.getCode(), e.getArgs());
		}
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, flashMessage);
		return "redirect:/login";
	}
}
