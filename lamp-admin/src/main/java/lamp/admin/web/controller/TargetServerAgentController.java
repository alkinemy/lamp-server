package lamp.admin.web.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.core.agent.domain.*;
import lamp.admin.core.agent.service.AgentManagementService;
import lamp.admin.core.agent.service.TargetServerService;
import lamp.admin.core.app.domain.AppTemplateDto;
import lamp.admin.core.app.service.AppTemplateService;
import lamp.admin.core.base.exception.Exceptions;
import lamp.admin.core.base.exception.LampErrorCode;
import lamp.admin.utils.StringUtils;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.security.SecurityUtils;
import lamp.admin.web.support.FlashMessage;
import lamp.admin.web.support.annotation.MenuMapping;
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
		agentManagementService.installAgent(id, editForm, SecurityUtils.getCurrentUserLogin());

		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));

		return "redirect:/target-server";
	}

	@RequestMapping(path = "/start", method = RequestMethod.GET)
	public String agentStart(@PathVariable("id") Long id,
							 @ModelAttribute("startForm") AgentStartForm startForm, Model model) throws IOException {

		TargetServerDto targetServer = targetServerService.getTargetServerDto(id);
		model.addAttribute("targetServer", targetServer);

		if (StringUtils.isBlank(startForm.getCommandLine())) {
			startForm.setCommandLine(targetServer.getAgentStartCommandLine());
		}

		return "target-server/agent/start";
	}


	@RequestMapping(path = "/start", method = RequestMethod.POST)
	public String agentStart(@PathVariable("id") Long id,
			@ModelAttribute("startForm") AgentStartForm startForm,
			Model model, RedirectAttributes redirectAttributes) throws IOException {

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PrintStream printStream = new PrintStream(baos)) {
			agentManagementService.startAgent(id, startForm, printStream);
			String output = baos.toString("UTF-8");

			FlashMessage flashMessage = FlashMessage.ofInfo(output);
			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, flashMessage);

			return "redirect:/target-server";
		}
	}

	@RequestMapping(path = "/stop", method = RequestMethod.GET)
	public String agentStop(@PathVariable("id") Long id,
			@ModelAttribute("stopForm") AgentStopForm stopForm, Model model) throws IOException {

		TargetServerDto targetServer = targetServerService.getTargetServerDto(id);
		model.addAttribute("targetServer", targetServer);

		if (StringUtils.isBlank(stopForm.getCommandLine())) {
			stopForm.setCommandLine(targetServer.getAgentStopCommandLine());
		}

		return "target-server/agent/stop";
	}

	@RequestMapping(path = "/stop", method = RequestMethod.POST)
	public String agentStop(@PathVariable("id") Long id,
			@ModelAttribute("stopForm") AgentStopForm stopForm,
			Model model, RedirectAttributes redirectAttributes) throws IOException {

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PrintStream printStream = new PrintStream(baos)) {
			agentManagementService.stopAgent(id, stopForm, printStream);
			String output = baos.toString("UTF-8");

			FlashMessage flashMessage = FlashMessage.ofInfo(output);
			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, flashMessage);

			return "redirect:/target-server";
		}
	}
}
