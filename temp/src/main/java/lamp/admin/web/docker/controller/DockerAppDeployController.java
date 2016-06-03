package lamp.admin.web.docker.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.domain.agent.model.TargetServerDto;
import lamp.admin.domain.agent.service.TargetServerService;
import lamp.admin.domain.base.exception.MessageException;
import lamp.admin.domain.docker.model.*;
import lamp.admin.domain.docker.service.DockerAppDeployService;
import lamp.admin.domain.docker.service.DockerAppService;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.FlashMessage;
import lamp.admin.web.support.annotation.MenuMapping;
import lamp.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@MenuMapping(MenuConstants.DOCKER_APPS)
@Controller
@RequestMapping("/docker/apps/{id}")
public class DockerAppDeployController {

	@Autowired
	private DockerAppService dockerAppService;

	@Autowired
	private DockerAppDeployService dockerAppDeployService;

	@Autowired
	private TargetServerService targetServerService;

	@RequestMapping(path = "/deploy", method = RequestMethod.GET)
	public String deploy(@PathVariable("id") String appId,
						 @ModelAttribute("editForm") DockerAppDeployForm editForm,
						 Model model) {
		model.addAttribute("action", LampAdminConstants.ACTION_CREATE);
		DockerApp dockerApp = dockerAppService.getDockerApp(appId);

		model.addAttribute("app", dockerApp);

		List<TargetServerDto> targetServerDtoList = targetServerService.getTargetServerDtoList();
		model.addAttribute("targetServers", targetServerDtoList);

		return "docker/app/deploy";
	}

	@RequestMapping(path = "/deploy", method = RequestMethod.POST)
	public String deploy(@PathVariable("id") String appId,
						 Model model,
						 @Valid @ModelAttribute("editForm") DockerAppDeployForm editForm,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {
		int serverSize = editForm.getTargetServerIds() != null ? editForm.getTargetServerIds().size() : 0;
		if (StringUtils.isNotBlank(editForm.getId())) {
			String[] ids = StringUtils.split(editForm.getId(), ',');
			if (ids.length == serverSize) {
				editForm.setIds(ids);
			} else {
				bindingResult.rejectValue("id", "AppTemplateDeployForm.id", "아이디 갯수는 서버 갯수와 일치해야합니다.");
			}
		} else {
			editForm.setIds(new String[serverSize]);
		}

		if (bindingResult.hasErrors()) {
			return deploy(appId, editForm, model);
		}

		try {
			dockerAppDeployService.deployApp(appId, editForm);
			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));

			return "redirect:/docker/apps";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return deploy(appId, editForm, model);
		}

	}
}
