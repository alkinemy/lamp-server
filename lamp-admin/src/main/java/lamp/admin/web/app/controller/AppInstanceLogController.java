package lamp.admin.web.app.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.core.app.base.AppInstance;
import lamp.admin.domain.app.base.service.AppInstanceManagementService;
import lamp.admin.domain.app.base.service.AppInstanceService;
import lamp.admin.domain.app.base.service.AppService;
import lamp.admin.domain.base.exception.MessageException;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.FlashMessage;
import lamp.admin.web.support.annotation.MenuMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
@MenuMapping(MenuConstants.APP)
@Controller
@RequestMapping("/apps")
public class AppInstanceLogController extends AbstractAppController {

	@Autowired
	private AppInstanceService appInstanceService;


	@Autowired
	private AppInstanceManagementService appInstanceManagementService;

	@Autowired
	public AppInstanceLogController(AppService appService) {
		super(appService);
	}


	@RequestMapping(path = "/**", method = RequestMethod.GET, params = {"action=stdout"})
	public String stdout(Model model,
						@ModelAttribute("path") String path,
						@RequestParam("instanceId") String instanceId,
						 RedirectAttributes redirectAttributes,
						 HttpServletResponse response) throws IOException {
		try {
			AppInstance appInstance = appInstanceService.getAppInstance(instanceId);

			String filename = "stdout.log";
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(filename, "UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding", "binary");

			appInstanceManagementService.transferStdOutStream(appInstance, response.getOutputStream());
			return "redirect:/apps/" + path;
		} catch (MessageException e) {
			log.warn("app start failed", e);
			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofError(e));

			return "redirect:/apps/" + path;
		}
	}

	@RequestMapping(path = "/**", method = RequestMethod.GET, params = {"action=stderr"})
	public String stderr(Model model,
						 @ModelAttribute("path") String path,
						 @RequestParam("instanceId") String instanceId,
						 RedirectAttributes redirectAttributes,
						 HttpServletResponse response) throws IOException {
		try {
			AppInstance appInstance = appInstanceService.getAppInstance(instanceId);

			String filename = "stderr.log";
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(filename, "UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding", "binary");

			appInstanceManagementService.transferStdErrStream(appInstance, response.getOutputStream());
			return "redirect:/apps/" + path;
		} catch (MessageException e) {
			log.warn("app start failed", e);
			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofError(e));

			return "redirect:/apps/" + path;
		}
	}


}
