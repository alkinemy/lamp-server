package lamp.admin.web.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.core.agent.domain.*;
import lamp.admin.core.agent.service.SshKeyService;
import lamp.admin.core.agent.service.TargetServerService;
import lamp.admin.core.base.exception.FlashMessageException;
import lamp.admin.utils.StringUtils;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.config.AgentProperties;
import lamp.admin.web.support.FlashMessage;
import lamp.admin.web.support.annotation.MenuMapping;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@MenuMapping(MenuConstants.SSH_KEY)
@Controller
@RequestMapping("/ssh-key")
public class SshKeyController {

	@Autowired
	private AgentProperties agentProperties;

	@Autowired
	private SshKeyService sshKeyService;


	@RequestMapping(path = "", method = RequestMethod.GET)
	public String list(Model model, Pageable pageable) {
		Page<SshKeyDto> page = sshKeyService.getSshKeyDtoList(pageable);
		model.addAttribute("page", page);
		return "ssh-key/list";
	}

	@RequestMapping(path = "/create", method = RequestMethod.GET)
	public String createForm(@ModelAttribute("editForm") SshKeyCreateForm editForm, Model model) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_CREATE);

		return "ssh-key/edit";
	}

	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("editForm") SshKeyCreateForm editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return createForm(editForm, model);
		}
		sshKeyService.insertSshKey(editForm);
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));


		return "redirect:/ssh-key";
	}

	@RequestMapping(path = "/{id}/update", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id,
						@ModelAttribute("editForm") SshKeyUpdateForm editForm, Model model) {
		SshKeyUpdateForm updateForm = sshKeyService.getSshKeyUpdateForm(id);
		BeanUtils.copyProperties(updateForm, editForm);

		return updateForm(editForm, model);
	}

	protected String updateForm(SshKeyUpdateForm editForm, Model model) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_UPDATE);

		model.addAttribute("sshAuthTypes", SshAuthType.values());
		return "ssh-key/edit";
	}

	@RequestMapping(path = "/{id}/update", method = RequestMethod.POST)
	public String update(@PathVariable("id") Long id, @ModelAttribute("editForm") SshKeyUpdateForm editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return updateForm(editForm, model);
		}

		sshKeyService.updateSshKey(id, editForm);
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.UPDATE_SUCCESS));

		return "redirect:/ssh-key";
	}

	@RequestMapping(path = "/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id
			, RedirectAttributes redirectAttributes) {

		FlashMessage flashMessage;
		try {
			sshKeyService.deleteSshKey(id);
			flashMessage = FlashMessage.ofSuccess(AdminErrorCode.DELETE_SUCCESS);
		} catch (FlashMessageException e) {
			flashMessage = FlashMessage.ofError(e.getMessage(), e.getCode(), e.getArgs());
		}
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, flashMessage);

		return "redirect:/ssh-key";
	}


}
