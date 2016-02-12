package lamp.server.aladin.admin.controller;

import lamp.server.aladin.LampConstants;
import lamp.server.aladin.admin.AdminErrorCode;
import lamp.server.aladin.admin.MenuConstants;
import lamp.server.aladin.admin.config.AgentProperties;
import lamp.server.aladin.admin.support.FlashMessage;
import lamp.server.aladin.admin.support.annotation.MenuMapping;
import lamp.server.aladin.core.domain.SshAuthType;
import lamp.server.aladin.core.dto.TargetServerCreateForm;
import lamp.server.aladin.core.dto.TargetServerDto;
import lamp.server.aladin.core.dto.TargetServerUpdateForm;
import lamp.server.aladin.core.exception.FlashMessageException;
import lamp.server.aladin.core.service.TargetServerService;
import lamp.server.aladin.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.EnumSet;

@MenuMapping(MenuConstants.TARGET_SERVER)
@Controller
@RequestMapping("/target-server")
public class TargetServerController {

	@Autowired
	private AgentProperties agentProperties;

	@Autowired
	private TargetServerService targetServerService;


	@RequestMapping(path = "", method = RequestMethod.GET)
	public String list(Model model, Pageable pageable) {
		Page<TargetServerDto> page = targetServerService.getTargetServerDtoList(pageable);
		model.addAttribute("page", page);
		return "target-server/list";
	}

	@RequestMapping(path = "/create", method = RequestMethod.GET)
	public String createForm(@ModelAttribute("editForm") TargetServerCreateForm editForm, Model model) {
		model.addAttribute("action", "create");
		if (StringUtils.isBlank(editForm.getAgentInstallPath())) {
			editForm.setAgentInstallPath(agentProperties.getInstallPath());
		}
		if (StringUtils.isBlank(editForm.getAgentStartCommandLine())) {
			editForm.setAgentStartCommandLine(agentProperties.getStartCommandLine());
		}

		EnumSet<SshAuthType> sshAuthTypes = EnumSet.allOf(SshAuthType.class);
		model.addAttribute("sshAuthTypes", sshAuthTypes);
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
		redirectAttributes.addFlashAttribute(LampConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));


		return "redirect:/target-server";
	}

	@RequestMapping(path = "/update", method = RequestMethod.GET)
	public String update(@ModelAttribute("editForm") TargetServerUpdateForm editForm, Model model) {
		TargetServerUpdateForm updateForm = targetServerService.getTargetServerUpdateForm(editForm.getId());
		BeanUtils.copyProperties(updateForm, editForm);

		return updateForm(editForm, model);
	}

	protected String updateForm(TargetServerUpdateForm editForm, Model model) {
		model.addAttribute("action", "update");

		model.addAttribute("sshAuthTypes", SshAuthType.values());
		return "target-server/edit";
	}

	@RequestMapping(path = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute("editForm") TargetServerUpdateForm editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return updateForm(editForm, model);
		}

		targetServerService.updateTargetServer(editForm);
		redirectAttributes.addFlashAttribute(LampConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.UPDATE_SUCCESS));

		return "redirect:/target-server";
	}

	@RequestMapping(path = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam("id") Long id
			, RedirectAttributes redirectAttributes) {

		FlashMessage flashMessage;
		try {
			targetServerService.deleteTargetServer(id);
			flashMessage = FlashMessage.ofSuccess(AdminErrorCode.DELETE_SUCCESS);
		} catch (FlashMessageException e) {
			flashMessage = FlashMessage.ofError(e.getMessage(), e.getCode(), e.getArgs());
		}
		redirectAttributes.addFlashAttribute(LampConstants.FLASH_MESSAGE_KEY, flashMessage);

		return "redirect:/target-server";
	}


}
