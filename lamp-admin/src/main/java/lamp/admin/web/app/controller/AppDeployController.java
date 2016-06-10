package lamp.admin.web.app.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.api.util.HttpServletRequestUtils;
import lamp.admin.core.app.base.App;
import lamp.admin.core.host.Host;
import lamp.admin.domain.app.base.model.form.AppDeployForm;
import lamp.admin.domain.app.base.model.form.GroupCreateForm;
import lamp.admin.domain.app.base.service.AppDeployService;
import lamp.admin.domain.app.base.service.AppService;
import lamp.admin.domain.base.exception.MessageException;
import lamp.admin.domain.host.service.HostService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@MenuMapping(MenuConstants.APP)
@Controller
@RequestMapping("/apps")
public class AppDeployController {

	@Autowired
	private AppService appService;

	@Autowired
	private HostService hostService;

	@Autowired
	private AppDeployService appDeployService;

	@ModelAttribute("path")
	protected String getPath(HttpServletRequest request) {
		String path = HttpServletRequestUtils.getRestPath(request);
		return path;
	}


	@RequestMapping(path = "/**", method = RequestMethod.GET, params = {"action=deploy"})
	public String deploy(Model model,
						 @ModelAttribute("path") String path,
						 @ModelAttribute("editForm") AppDeployForm editForm) {


		return deployForm(model, path, editForm);
	}

	protected String deployForm(Model model, String path, AppDeployForm editForm) {
		App app = appService.getApp(path);
		model.addAttribute("app", app);
		List<Host> hosts = hostService.getHostsByClusterId(app.getClusterId());
		model.addAttribute("hosts", hosts);

		return "apps/deploy";
	}

	@RequestMapping(path = "/**", method = RequestMethod.POST, params = {"action=deploy"})
	public String deploy(Model model,
						 @ModelAttribute("path") String path,
						 @ModelAttribute("editForm") AppDeployForm editForm,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {
		try {
			appDeployService.deploy(path, editForm.getHostIds());
			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));

			redirectAttributes.addAttribute("path", path);
			redirectAttributes.addAttribute("action", "deploy");
			return "redirect:/apps/{path}";
		} catch (MessageException e) {
			log.warn("deploy failed", e);
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return deployForm(model, path, editForm);
		}
	}

//
//
//	@RequestMapping(path = "/deploy", method = RequestMethod.GET)
//	public String deploy(@PathVariable("id") String templateId,
//						 @ModelAttribute("editForm") AppTemplateDeployForm editForm,
//						 Model model, HttpMethod httpMethod) {
//		model.addAttribute("action", LampAdminConstants.ACTION_CREATE);
//
//		AppTemplateDto appTemplateDto = appTemplateService.getAppTemplateDtoOptional(templateId);
//
//		List<AppInstallScriptDto> appInstallScriptDtoList = appInstallScriptService.getAppInstallScriptDtoList(appTemplateDto.getId());
//		model.addAttribute("appInstallScripts", appInstallScriptDtoList);
//
//		model.addAttribute("appTemplate", appTemplateDto);
//		model.addAttribute("parametersTypes", ParametersType.values());
//
//		List<String> versions = appRepoService.getVersions(appTemplateDto.getRepositoryId(), appTemplateDto.getGroupId(), appTemplateDto.getArtifactId());
//		model.addAttribute("versions", versions);
//
//
//		List<TargetServerDto> targetServerDtoList = targetServerService.getTargetServerDtoList();
//		model.addAttribute("targetServers", targetServerDtoList);
//
//		if (httpMethod.equals(HttpMethod.GET)) {
//			editForm.setId("");
//			editForm.setName(appTemplateDto.getName());
//
//			editForm.setParametersType(appTemplateDto.getParametersType());
//			editForm.setParameters(appTemplateDto.getParameters());
//			editForm.setVersion(versions.stream().findFirst().orElse(null));
//		}
//		return "app/template/deploy";
//	}
//
//	@RequestMapping(path = "/deploy", method = RequestMethod.POST)
//	public String deploy(@PathVariable("id") String templateId,
//						 @Valid @ModelAttribute("editForm") AppTemplateDeployForm editForm,
//						 Model model,
//						 BindingResult bindingResult,
//						 RedirectAttributes redirectAttributes) {
//		int serverSize = editForm.getTargetServerIds() != null ? editForm.getTargetServerIds().size() : 0;
//		if (StringUtils.isNotBlank(editForm.getId())) {
//			String[] ids = StringUtils.split(editForm.getId(), ',');
//			if (ids.length == serverSize) {
//				editForm.setIds(ids);
//			} else {
//				bindingResult.rejectValue("id", "AppTemplateDeployForm.id", "아이디 갯수는 서버 갯수와 일치해야합니다.");
//			}
//		} else {
//			editForm.setIds(new String[serverSize]);
//		}
//
//		if (bindingResult.hasErrors()) {
//			return deploy(templateId, editForm, model, HttpMethod.POST);
//		}
//
//		try {
//			editForm.setTemplateId(templateId);
//			appFacadeService.deployApp(editForm);
//
//			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));
//
//			return "redirect:/app/template";
//		} catch (MessageException e) {
//			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
//			return deploy(templateId, editForm, model, HttpMethod.POST);
//		}
//
//	}


}
