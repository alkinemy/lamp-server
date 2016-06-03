package lamp.admin.web.server.controller;

import lamp.admin.domain.agent.model.MultiAgentInstallForm;
import lamp.admin.domain.agent.model.TargetServerDto;
import lamp.admin.domain.agent.service.AgentManagementService;
import lamp.admin.domain.agent.service.TargetServerService;
import lamp.admin.domain.app.model.AppInstallScriptDto;
import lamp.admin.domain.app.model.AppTemplateDto;
import lamp.admin.domain.app.service.AppInstallScriptService;
import lamp.admin.domain.app.service.AppRepoService;
import lamp.admin.domain.app.service.AppTemplateService;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.annotation.MenuMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@MenuMapping(MenuConstants.AGENT_INSTALL)
@Controller
@RequestMapping("/server/target-server/agent")
public class TargetServerAgentMultiController {

	@Autowired
	private TargetServerService targetServerService;

	@Autowired
	private AppTemplateService appTemplateService;

	@Autowired
	private AgentManagementService agentManagementService;

	@Autowired
	private AppRepoService appRepoService;

	@Autowired
	private AppInstallScriptService appInstallScriptService;

	@RequestMapping(path = "/install-step1", method = RequestMethod.GET)
	public String agentInstallStep1(@ModelAttribute("editForm") MultiAgentInstallForm editForm, Model model) {

		List<TargetServerDto> targetServerDtos = targetServerService.getTargetServerDtos(editForm.getTargetServerIds());
		model.addAttribute("targetServers", targetServerDtos);

		List<AppTemplateDto> appTemplateList = appTemplateService.getAppTemplateDtoList();
		model.addAttribute("appTemplateList", appTemplateList);

		return "server/target-server/agent/install-step1";
	}

	@RequestMapping(path = "/install-step1", method = RequestMethod.POST)
	public String agentInstallStep1(@ModelAttribute("editForm") MultiAgentInstallForm editForm,
									Model model,
									BindingResult bindingResult) {

		return agentInstall(editForm, model);
	}

	@RequestMapping(path = "/install", method = RequestMethod.GET)
	public String agentInstall(@ModelAttribute("editForm") MultiAgentInstallForm editForm, Model model) {

		List<TargetServerDto> targetServerDtos = targetServerService.getTargetServerDtos(editForm.getTargetServerIds());
		model.addAttribute("targetServers", targetServerDtos);

		AppTemplateDto appTemplateDto = appTemplateService.getAppTemplateDto(editForm.getTemplateId());
		model.addAttribute("appTemplate", appTemplateDto);

		List<String> versions = appRepoService.getVersions(appTemplateDto.getRepositoryId(), appTemplateDto.getGroupId(), appTemplateDto.getArtifactId());
		model.addAttribute("versions", versions);

		List<AppInstallScriptDto> appInstallScriptDtoList = appInstallScriptService.getAppInstallScriptDtoList(appTemplateDto.getId());
		model.addAttribute("appInstallScripts", appInstallScriptDtoList);

		return "server/target-server/agent/install";
	}

	@RequestMapping(path = "/install", method = RequestMethod.POST)
	public String agentInstall(@Valid @ModelAttribute("editForm") MultiAgentInstallForm editForm,
				BindingResult bindingResult, Model model,
				RedirectAttributes redirectAttributes) throws IOException {
		if (bindingResult.hasErrors()) {
			return agentInstall(editForm, model);
		}
//		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				PrintStream printStream = new PrintStream(baos)) {
//
//			agentManagementService.installAgent(id, editForm, SecurityUtils.getCurrentUserLogin(), printStream);
//
//			String output = baos.toString("UTF-8");
//			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));
//
//			return "redirect:/server/target-server";
//		} catch (MessageException e) {
//			String defaultMessage = e.getMessage();
//			if (e.getCause() != null) {
//				defaultMessage = defaultMessage + ("(" + e.getCause().getMessage() + ")");
//			}
//
//			bindingResult.reject(e.getCode(), e.getArgs(), defaultMessage);
//			return agentInstall(id, editForm, model);
//		}
		return "";
	}

//	@MenuMapping(MenuConstants.AGENT_START)
//	@RequestMapping(path = "/start", method = RequestMethod.GET)
//	public String agentStart(@PathVariable("id") String id,
//							 @ModelAttribute("startForm") AgentStartForm startForm, Model model) throws IOException {
//
//		TargetServerDto targetServer = targetServerService.getTargetServerDto(id);
//		model.addAttribute("targetServer", targetServer);
//
//		if (StringUtils.isBlank(startForm.getCommandLine())) {
//			startForm.setCommandLine(targetServer.getAgentStartCommandLine());
//		}
//
//		return "server/target-server/agent/start";
//	}
//
//
//	@MenuMapping(MenuConstants.AGENT_START)
//	@RequestMapping(path = "/start", method = RequestMethod.POST)
//	public String agentStart(@PathVariable("id") String id,
//			@ModelAttribute("startForm") AgentStartForm startForm,
//			Model model, RedirectAttributes redirectAttributes) throws IOException {
//
//		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				PrintStream printStream = new PrintStream(baos)) {
//			agentManagementService.startAgent(id, startForm, printStream);
//			String output = baos.toString("UTF-8");
//
//			FlashMessage flashMessage = FlashMessage.ofInfo(output);
//			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, flashMessage);
//
//			return "redirect:/server/target-server";
//		}
//	}
//
//	@MenuMapping(MenuConstants.AGENT_STOP)
//	@RequestMapping(path = "/stop", method = RequestMethod.GET)
//	public String agentStop(@PathVariable("id") String id,
//			@ModelAttribute("stopForm") AgentStopForm stopForm, Model model) throws IOException {
//
//		TargetServerDto targetServer = targetServerService.getTargetServerDto(id);
//		model.addAttribute("targetServer", targetServer);
//
//		if (StringUtils.isBlank(stopForm.getCommandLine())) {
//			stopForm.setCommandLine(targetServer.getAgentStopCommandLine());
//		}
//
//		return "server/target-server/agent/stop";
//	}
//
//	@MenuMapping(MenuConstants.AGENT_STOP)
//	@RequestMapping(path = "/stop", method = RequestMethod.POST)
//	public String agentStop(@PathVariable("id") String id,
//			@ModelAttribute("stopForm") AgentStopForm stopForm,
//			Model model, RedirectAttributes redirectAttributes) throws IOException {
//
//		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				PrintStream printStream = new PrintStream(baos)) {
//			agentManagementService.stopAgent(id, stopForm, printStream);
//			String output = baos.toString("UTF-8");
//
//			FlashMessage flashMessage = FlashMessage.ofInfo(output);
//			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, flashMessage);
//
//			return "redirect:/server/target-server";
//		}
//	}
}
