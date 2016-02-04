package lamp.server.aladin.admin.controller;

import lamp.server.aladin.LampConstants;
import lamp.server.aladin.admin.AdminErrorCode;
import lamp.server.aladin.admin.MenuConstants;
import lamp.server.aladin.admin.support.FlashMessage;
import lamp.server.aladin.admin.support.annotation.MenuMapping;
import lamp.server.aladin.core.domain.TargetServer;
import lamp.server.aladin.core.dto.AgentInstallForm;
import lamp.server.aladin.core.dto.AgentStartForm;
import lamp.server.aladin.core.dto.AppTemplateDto;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.core.exception.LampErrorCode;
import lamp.server.aladin.core.service.AgentManagementService;
import lamp.server.aladin.core.service.AppTemplateService;
import lamp.server.aladin.core.service.TargetServerService;
import lamp.server.aladin.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;

@MenuMapping(MenuConstants.TARGET_SERVER_AGENT)
@Controller
@RequestMapping("/target-server/{id}/agent")
public class TargetServerAgentController {

	@Autowired
	private TargetServerService targetServerService;

	@Autowired
	private AppTemplateService appTemplateService;


	@Autowired
	private AgentManagementService agentManagementService;

	@RequestMapping(path = "/install", method = RequestMethod.GET)
	public String agentInstallForm(@PathVariable("id") Long id,
			@ModelAttribute("editForm") AgentInstallForm editForm, Model model) {

		Optional<TargetServer> targetServerOptional = targetServerService.getTargetServerOptional(id);
		TargetServer targetServer = targetServerOptional.orElseThrow(() -> Exceptions.newException(LampErrorCode.TARGET_SERVER_NOT_FOUND, id));

		model.addAttribute("targetServer", targetServer);
		if (StringUtils.isBlank(editForm.getUsername())) {
			editForm.setUsername(targetServer.getUsername());
		}
		if (StringUtils.isBlank(editForm.getPassword())) {
			editForm.setPassword(targetServer.getPassword());
		}

		List<AppTemplateDto> appTemplateList = appTemplateService.getAppTemplateDtoList();
		model.addAttribute("appTemplateList", appTemplateList);

		return "target-server/agent/install";
	}



	@RequestMapping(path = "/install", method = RequestMethod.POST)
	public String agentInstall(@PathVariable("id") Long id,
			@Valid @ModelAttribute("editForm") AgentInstallForm editForm,
				BindingResult bindingResult, Model model,
				RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return agentInstallForm(id, editForm, model);
		}
		agentManagementService.installAgent(id, editForm);

		redirectAttributes.addFlashAttribute(LampConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));

		return "redirect:/target-server";
	}

	@RequestMapping(path = "/start", method = RequestMethod.GET)
	public String agentStart(@PathVariable("id") Long id,
			@ModelAttribute("startForm") AgentStartForm startForm, Model model) throws IOException {

		Optional<TargetServer> targetServerOptional = targetServerService.getTargetServerOptional(id);
		TargetServer targetServer = targetServerOptional.orElseThrow(() -> Exceptions.newException(LampErrorCode.TARGET_SERVER_NOT_FOUND, id));

		model.addAttribute("targetServer", targetServer);
		if (StringUtils.isBlank(startForm.getUsername())) {
			startForm.setUsername(targetServer.getUsername());
		}

		return "target-server/agent/start";
	}

	@RequestMapping(path = "/start", method = RequestMethod.POST)
	public String agentStart(@PathVariable("id") Long id,
			@ModelAttribute("startForm") AgentStartForm startForm,
			Model model, RedirectAttributes redirectAttributes) throws IOException {

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PrintStream printStream = new PrintStream(baos)) {
			agentManagementService.startAgent(id, startForm.getUsername(), startForm.getPassword(), printStream);
			String output = baos.toString("UTF-8");

			FlashMessage flashMessage = FlashMessage.ofInfo(output);
			redirectAttributes.addFlashAttribute(LampConstants.FLASH_MESSAGE_KEY, flashMessage);

			return "redirect:/target-server";
		}
	}

}
