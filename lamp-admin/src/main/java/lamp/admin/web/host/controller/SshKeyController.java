package lamp.admin.web.host.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.domain.base.exception.MessageException;
import lamp.admin.domain.host.model.SshKey;
import lamp.admin.domain.host.service.SshKeyService;
import lamp.admin.domain.host.model.form.SshKeyForm;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.annotation.MenuMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@MenuMapping(MenuConstants.SSH_KEYS)
@Controller
@RequestMapping("/ssh-keys")
public class SshKeyController {

	@Autowired
	private SshKeyService sshKeyService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		List<SshKey> sshKeys = sshKeyService.getSshKeyList();
		model.addAttribute("sshKeys", sshKeys);
		return "ssh-keys/list";
	}


	@RequestMapping(path = "/add", method = RequestMethod.GET)
	public String add(Model model,
						 @ModelAttribute("editForm") SshKeyForm editForm) {
		return addForm(model, editForm);
	}

	protected String addForm(Model model, SshKeyForm editForm) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_CREATE);
		return "ssh-keys/edit";
	}

	@RequestMapping(path = "/add", method = RequestMethod.POST)
	public String add(Model model,
						 @ModelAttribute("editForm") SshKeyForm editForm,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {
		try {
			sshKeyService.addSshKey(editForm);
			return "redirect:/ssh-keys";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return addForm(model, editForm);
		}
	}

	@RequestMapping(path = "/{sshKeyId}/update", method = RequestMethod.GET)
	public String update(Model model,
						 @PathVariable("sshKeyId") String sshKeyId,
						 @ModelAttribute("editForm") SshKeyForm editForm) {
		SshKeyForm form = sshKeyService.getSshKeyFormForUpdate(sshKeyId);
		BeanUtils.copyProperties(form, editForm);
		return updateForm(model, editForm);
	}

	protected String updateForm(Model model, SshKeyForm editForm) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_UPDATE);
		return "ssh-keys/edit";
	}

	@RequestMapping(path = "/{sshKeyId}/update", method = RequestMethod.POST)
	public String update(Model model,
						 @PathVariable("sshKeyId") String sshKeyId,
						 @ModelAttribute("editForm") SshKeyForm editForm,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {
		try {
			sshKeyService.updateSshKey(sshKeyId, editForm);
			return "redirect:/ssh-keys";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return addForm(model, editForm);
		}
	}

	@RequestMapping(path = "/{sshKeyId}/delete", method = RequestMethod.GET)
	public String delete(Model model,
						 @PathVariable("sshKeyId") String sshKeyId) {
		sshKeyService.deleteSshKey(sshKeyId);
		return "redirect:/ssh-keys";
	}


}
