package lamp.admin.web.app.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.core.app.base.App;
import lamp.admin.core.host.AwsCluster;
import lamp.admin.domain.app.base.model.form.AppInstanceClusterDeployForm;
import lamp.admin.domain.app.base.model.form.AppInstanceDeployForm;
import lamp.admin.domain.app.base.service.AppInstanceManagementService;
import lamp.admin.domain.app.base.service.AppInstanceService;
import lamp.admin.domain.app.base.service.AppService;
import lamp.admin.domain.app.base.service.AwsEc2AppInstanceManagementService;
import lamp.admin.domain.base.exception.MessageException;
import lamp.admin.domain.host.service.ClusterService;
import lamp.admin.domain.host.service.HostFacadeService;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.MenuConstants;
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

import java.util.List;

@Slf4j
@MenuMapping(MenuConstants.APP)
@Controller
@RequestMapping("/apps")
public class AppInstanceClusterDeployController extends AbstractAppController {


	@Autowired
	private AwsEc2AppInstanceManagementService awsEc2AppInstanceManagementService;

	@Autowired
	private ClusterService clusterService;

	@Autowired
	public AppInstanceClusterDeployController(AppService appService) {
		super(appService);
	}

	@RequestMapping(path = "/**", method = RequestMethod.GET, params = {"action=cluster-deploy"})
	public String deploy(Model model,
						 @ModelAttribute("path") String path,
						 @ModelAttribute("editForm") AppInstanceClusterDeployForm editForm) {


		return deployForm(model, path, editForm);
	}

	protected String deployForm(Model model, String path, AppInstanceClusterDeployForm editForm) {
		App app = appService.getAppByPath(path);
		model.addAttribute("app", app);

		List<AwsCluster> clusters = clusterService.getAwsClusterList();
		model.addAttribute("clusters", clusters);

		return "apps/cluster-deploy";
	}

	@RequestMapping(path = "/**", method = RequestMethod.POST, params = {"action=cluster-deploy"})
	public String deploy(Model model,
						 @ModelAttribute("path") String path,
						 @ModelAttribute("editForm") AppInstanceClusterDeployForm editForm,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {
		try {
			awsEc2AppInstanceManagementService.deploy(path, editForm.getClusterId(), editForm.getMinCount(), editForm.getMaxCount(), null);
			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));

			return "redirect:/apps/" + path;
		} catch (MessageException e) {
			log.warn("app deploy failed", e);
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return deployForm(model, path, editForm);
		}
	}

	@RequestMapping(path = "/**", method = {RequestMethod.GET, RequestMethod.POST}, params = {"action=cluster-undeploy"})
	public String undeploy(Model model,
						   @ModelAttribute("path") String path,
						   @RequestParam("instanceId") List<String> instanceIds,
						   @RequestParam(name = "forceStop", defaultValue = "false") boolean forceStop,
						   RedirectAttributes redirectAttributes) {
		try {
			awsEc2AppInstanceManagementService.undeploy(path, instanceIds);
			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.DELETE_SUCCESS));

			return "redirect:/apps/" + path;
		} catch (MessageException e) {
			log.warn("app undeploy failed", e);
			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofError(e));

			return "redirect:/apps/" + path;
		}
	}




}
