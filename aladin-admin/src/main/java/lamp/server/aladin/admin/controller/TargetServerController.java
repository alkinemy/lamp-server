package lamp.server.aladin.admin.controller;

import lamp.server.aladin.admin.MenuConstants;
import lamp.server.aladin.admin.support.annotation.MenuMapping;
import lamp.server.aladin.core.domain.TargetServer;
import lamp.server.aladin.core.dto.AgentInstallForm;
import lamp.server.aladin.core.dto.TargetServerCreateForm;
import lamp.server.aladin.core.dto.TargetServerDto;
import lamp.server.aladin.core.exception.Exceptions;
import lamp.server.aladin.core.exception.LampErrorCode;
import lamp.server.aladin.core.service.AgentManagementService;
import lamp.server.aladin.core.service.TargetServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import java.util.Optional;

@MenuMapping(MenuConstants.TARGET_SERVER)
@Controller
@RequestMapping("/target-server")
public class TargetServerController {

	@Autowired
	private TargetServerService targetServerService;

	@Autowired
	private AgentManagementService agentManagementService;

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String list(Model model, Pageable pageable) {
		Page<TargetServerDto> page = targetServerService.getTargetServerList(pageable);
		model.addAttribute("page", page);
		return "target-server/list";
	}

	@RequestMapping(path = "/create", method = RequestMethod.GET)
	public String createForm(@ModelAttribute("editForm") TargetServerCreateForm editForm, Model model) {
		model.addAttribute("action", "create");
		return "target-server/edit";
	}

	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("editForm") TargetServerCreateForm editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return createForm(editForm, model);
		}
		targetServerService.insertTargetServer(editForm);
		redirectAttributes.addFlashAttribute("flashMessage", "성공적으로 등록하였습니다.");


		return "redirect:/target-server";
	}

	@RequestMapping(path = "/update", method = RequestMethod.GET)
	public String updateForm(@ModelAttribute("editForm") TargetServerCreateForm editForm, Model model) {
		model.addAttribute("action", "update");
		return "target-server/edit";
	}

	@RequestMapping(path = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute("editForm") TargetServerCreateForm editForm) {

		return "target-server/edit";
	}

	@RequestMapping(path = "/{id}/agent/install", method = RequestMethod.GET)
	public String agentInstallForm(@PathVariable("id") Long id,
			@ModelAttribute("editForm") AgentInstallForm editForm, Model model) {

		Optional<TargetServer> targetServerOptional = targetServerService.getTargetServer(id);
		TargetServer targetServer = targetServerOptional.orElseThrow(() -> Exceptions.newException(LampErrorCode.TARGET_SERVER_NOT_FOUND, id));

		model.addAttribute("targetServer", targetServer);


		return "target-server/agent/edit";
	}

	@RequestMapping(path = "/{id}/agent/install", method = RequestMethod.POST)
	public String agentInstall(@PathVariable("id") Long id,
			@Valid @ModelAttribute("editForm") AgentInstallForm editForm,
				BindingResult bindingResult, Model model,
				RedirectAttributes redirectAttributes) {
			if (bindingResult.hasErrors()) {
				return agentInstallForm(id, editForm, model);
			}
			agentManagementService.installAgent(id, editForm);

			redirectAttributes.addFlashAttribute("flashMessage", "성공적으로 등록하였습니다.");

		return "redirect:/target-server";
	}

	@RequestMapping(path = "/{id}/agent/start", method = RequestMethod.GET)
	public String agentStart(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) throws IOException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PrintStream printStream = new PrintStream(baos)) {
			agentManagementService.startAgent(id, printStream);
			String output = baos.toString("UTF-8");

			redirectAttributes.addFlashAttribute("flashMessage", output);

			return "redirect:/target-server";
		}
	}

}
