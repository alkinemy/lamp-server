package lamp.admin.web.server.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.config.web.AgentProperties;
import lamp.admin.domain.agent.model.*;
import lamp.admin.domain.agent.service.SshKeyService;
import lamp.admin.domain.agent.service.TargetServerService;
import lamp.admin.domain.base.exception.FlashMessageException;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.FlashMessage;
import lamp.admin.web.support.annotation.MenuMapping;
import lamp.common.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@MenuMapping(MenuConstants.TARGET_SERVER)
@Controller
@RequestMapping("/server/target-server")
public class TargetServerController {

	@Autowired
	private AgentProperties agentProperties;

	@Autowired
	private TargetServerService targetServerService;

	@Autowired
	private SshKeyService sshKeyService;


	@RequestMapping(path = "", method = RequestMethod.GET)
	public String list(Model model, @SortDefault(value = "name", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<TargetServerDto> page = targetServerService.getTargetServerDtoList(pageable);
		model.addAttribute("page", page);
		return "server/target-server/list";
	}

	@RequestMapping(path = "/create", method = RequestMethod.GET)
	public String createForm(@ModelAttribute("editForm") TargetServerCreateForm editForm, Model model) {
		model.addAttribute("action", "create");
		if (StringUtils.isBlank(editForm.getAgentInstallPath())) {
			editForm.setAgentInstallPath(agentProperties.getInstallPath());
		}

		model.addAttribute("sshAuthTypes", SshAuthType.values());
		List<SshKeyDto> sshKeyDtoList = sshKeyService.getSshKeyDtoList();
		model.addAttribute("sshKeys", sshKeyDtoList);
		return "server/target-server/edit";
	}

	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("editForm") TargetServerCreateForm editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return createForm(editForm, model);
		}
		targetServerService.insertTargetServer(editForm);
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));


		return "redirect:/server/target-server";
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
		List<SshKeyDto> sshKeyDtoList = sshKeyService.getSshKeyDtoList();
		model.addAttribute("sshKeys", sshKeyDtoList);

		return "server/target-server/edit";
	}

	@RequestMapping(path = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute("editForm") TargetServerUpdateForm editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return updateForm(editForm, model);
		}

		targetServerService.updateTargetServer(editForm);
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.UPDATE_SUCCESS));

		return "redirect:/server/target-server";
	}

	@RequestMapping(path = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam("id") String id
			, RedirectAttributes redirectAttributes) {

		FlashMessage flashMessage;
		try {
			targetServerService.deleteTargetServer(id);
			flashMessage = FlashMessage.ofSuccess(AdminErrorCode.DELETE_SUCCESS);
		} catch (FlashMessageException e) {
			flashMessage = FlashMessage.ofError(e.getMessage(), e.getCode(), e.getArgs());
		}
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, flashMessage);

		return "redirect:/server/target-server";
	}


}
