package lamp.admin.web.app.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.api.util.HttpServletRequestUtils;
import lamp.admin.core.app.base.App;
import lamp.admin.core.app.base.AppInstance;
import lamp.admin.core.host.Host;
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
public class AppInstanceController extends AbstractAppController {

	@Autowired
	private AppInstanceService appInstanceService;

	@Autowired
	private HostService hostService;

	@Autowired
	private AppInstanceDeployService appInstanceDeployService;

	@Autowired
	public AppInstanceController(AppService appService) {
		super(appService);
	}

	@RequestMapping(path = "/**", method = RequestMethod.GET, params = {"action=deploy"})
	public String deploy(Model model,
						 @ModelAttribute("path") String path,
						 @ModelAttribute("editForm") AppCreateForm editForm) {


		return deployForm(model, path, editForm);
	}

	protected String deployForm(Model model, String path, AppCreateForm editForm) {
		App app = appService.getApp(path);
		model.addAttribute("app", app);
		List<Host> hosts = hostService.getHostsByClusterId(app.getClusterId());
		model.addAttribute("hosts", hosts);

		return "apps/deploy";
	}

	@RequestMapping(path = "/**", method = RequestMethod.POST, params = {"action=deploy"})
	public String deploy(Model model,
						 @ModelAttribute("path") String path,
						 @ModelAttribute("editForm") AppCreateForm editForm,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {
		try {
			appInstanceDeployService.deploy(path, editForm.getHostIds());
			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));

			return "redirect:/apps/" + path;
		} catch (MessageException e) {
			log.warn("app deploy failed", e);
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return deployForm(model, path, editForm);
		}
	}

	@RequestMapping(path = "/**", method = RequestMethod.GET, params = {"action=undeploy"})
	public String undeploy(Model model,
						   @ModelAttribute("path") String path,
						   @RequestParam("instanceId") String instanceId,
						   @RequestParam("forceStop") boolean forceStop,
						   RedirectAttributes redirectAttributes) {
		try {
			AppInstance appInstance = appInstanceService.getAppInstance(instanceId);

			appInstanceDeployService.undeploy(appInstance, forceStop);
			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.DELETE_SUCCESS));

			return "redirect:/apps/" + path;
		} catch (MessageException e) {
			log.warn("app destroy failed", e);
			redirectAttributes.addFlashAttribute("path", path);
			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofError(e));

			return "redirect:/apps/{path}";
		}
	}

	@RequestMapping(path = "/**", method = RequestMethod.GET, params = {"action=start"})
	public String start(Model model,
						@ModelAttribute("path") String path,
						@RequestParam("instanceId") String instanceId,
						RedirectAttributes redirectAttributes) {
		try {
			AppInstance appInstance = appInstanceService.getAppInstance(instanceId);

			appInstanceDeployService.start(appInstance);
			redirectAttributes.addFlashAttribute("path", path);
			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));

			return "redirect:/apps/" + path;
		} catch (MessageException e) {
			log.warn("app start failed", e);
			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofError(e));

			return "redirect:/apps/" + path;
		}
	}

	@RequestMapping(path = "/**", method = RequestMethod.GET, params = {"action=stop"})
	public String stop(Model model,
						@ModelAttribute("path") String path,
						@RequestParam("instanceId") String instanceId,
						RedirectAttributes redirectAttributes) {
		try {
			AppInstance appInstance = appInstanceService.getAppInstance(instanceId);

			appInstanceDeployService.stop(appInstance);
			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));

			return "redirect:/apps/" + path;
		} catch (MessageException e) {
			log.warn("app start failed", e);
			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofError(e));

			return "redirect:/apps/" + path;
		}
	}

}
