package lamp.admin.web.app.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.api.util.HttpServletRequestUtils;
import lamp.admin.core.app.base.App;
import lamp.admin.core.app.base.AppInstance;
import lamp.admin.core.host.Host;
import lamp.admin.domain.app.base.model.entity.AppType;
import lamp.admin.domain.app.base.model.form.AppCreateForm;
import lamp.admin.domain.app.base.service.AppInstanceDeployService;
import lamp.admin.domain.app.base.service.AppInstanceService;
import lamp.admin.domain.app.base.service.AppService;
import lamp.admin.domain.base.exception.MessageException;
import lamp.admin.domain.host.service.HostService;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.account.model.MenuItem;
import lamp.admin.web.support.FlashMessage;
import lamp.admin.web.support.annotation.MenuMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@MenuMapping(MenuConstants.APP)
@Controller
@RequestMapping("/apps")
public class AppController extends AbstractAppController {

	@Autowired
	private AppInstanceService appInstanceService;

	@Autowired
	public AppController(AppService appService) {
		super(appService);
	}

	@RequestMapping(path = "/**", method = RequestMethod.GET)
	public String list(Model model,
					   @ModelAttribute("path") String path) {
		App app = appService.getApp(path);
		model.addAttribute("breadcrumb", getBreadcrumb(app));
		if (AppType.GROUP.equals(app.getType())) {
			List<App> apps = appService.getAppsByPath(path);
			model.addAttribute("apps", apps);
			return "apps/list";
		} else {
			model.addAttribute("app", app);
			List<AppInstance> appInstances = appInstanceService.getAppInstances(app.getId());
			model.addAttribute("appInstances", appInstances);
			return "apps/view";
		}
	}

	@RequestMapping(path = "/**", method = RequestMethod.GET, params = {"action=destroy"})
	public String destroy(Model model,
						  @ModelAttribute("path") String path,
						  @RequestParam(name = "forceDestroy", defaultValue = "false") boolean forceDestroy,
						  RedirectAttributes redirectAttributes) {
		try {
			App app = appService.getApp(path);

			appService.destroy(app, forceDestroy);
			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.DELETE_SUCCESS));

			redirectAttributes.addAttribute("path", path);
			return "redirect:/apps/" + path;
		} catch (MessageException e) {
			log.warn("app destroy failed", e);
			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofError(e));
			redirectAttributes.addAttribute("path", path);

			return "redirect:/apps/" + path;
		}
	}


}
