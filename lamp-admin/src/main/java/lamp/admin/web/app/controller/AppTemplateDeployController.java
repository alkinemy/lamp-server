package lamp.admin.web.app.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.domain.agent.model.TargetServerDto;
import lamp.admin.domain.agent.service.TargetServerService;
import lamp.admin.domain.app.model.AppInstallScriptDto;
import lamp.admin.domain.app.model.AppTemplateDeployForm;
import lamp.admin.domain.app.model.AppTemplateDto;
import lamp.admin.domain.app.model.ParametersType;
import lamp.admin.domain.app.service.AppFacadeService;
import lamp.admin.domain.app.service.AppInstallScriptService;
import lamp.admin.domain.app.service.AppRepoService;
import lamp.admin.domain.app.service.AppTemplateService;
import lamp.admin.domain.base.exception.MessageException;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.FlashMessage;
import lamp.admin.web.support.annotation.MenuMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@MenuMapping(MenuConstants.APP_TEMPLATE)
@Controller
@RequestMapping("/app/template/{id}")
public class AppTemplateDeployController {

	@Autowired
	private AppTemplateService appTemplateService;

	@Autowired
	private AppInstallScriptService appInstallScriptService;

	@Autowired
	private AppRepoService appRepoService;

	@Autowired
	private TargetServerService targetServerService;

	@Autowired
	private AppFacadeService appFacadeService;


	@RequestMapping(path = "/deploy", method = RequestMethod.GET)
	public String deploy(@PathVariable("id") String templateId,
						 @ModelAttribute("editForm") AppTemplateDeployForm editForm,
						 Model model, HttpMethod httpMethod) {
		model.addAttribute("action", LampAdminConstants.ACTION_CREATE);

		AppTemplateDto appTemplateDto = appTemplateService.getAppTemplateDtoOptional(templateId);

		List<AppInstallScriptDto> appInstallScriptDtoList = appInstallScriptService.getAppInstallScriptDtoList(appTemplateDto.getId());
		model.addAttribute("appInstallScripts", appInstallScriptDtoList);

		model.addAttribute("appTemplate", appTemplateDto);
		model.addAttribute("parametersTypes", ParametersType.values());

		List<String> versions = appRepoService.getVersions(appTemplateDto.getRepositoryId(), appTemplateDto.getGroupId(), appTemplateDto.getArtifactId());
		model.addAttribute("versions", versions);


		List<TargetServerDto> targetServerDtoList = targetServerService.getTargetServerDtoList();
		model.addAttribute("targetServers", targetServerDtoList);

		if (httpMethod.equals(HttpMethod.GET)) {
			editForm.setId(appTemplateDto.getArtifactId());
			editForm.setName(appTemplateDto.getName());

			editForm.setParametersType(appTemplateDto.getParametersType());
			editForm.setParameters(appTemplateDto.getParameters());
			editForm.setVersion(versions.stream().findFirst().orElse(null));
		}
		return "app/template/deploy";
	}

	@RequestMapping(path = "/deploy", method = RequestMethod.POST)
	public String deploy(@PathVariable("id") String templateId,
						 @Valid @ModelAttribute("editForm") AppTemplateDeployForm editForm,
						 Model model,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return deploy(templateId, editForm, model, HttpMethod.POST);
		}

		try {
			editForm.setTemplateId(templateId);
			appFacadeService.registerApp(editForm);

			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));

			return "redirect:/app/template";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return deploy(templateId, editForm, model, HttpMethod.POST);
		}

	}


}
