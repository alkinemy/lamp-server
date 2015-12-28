package lamp.server.aladin.core.controller;

import lamp.server.aladin.core.dto.TargetServerCreateForm;
import lamp.server.aladin.core.dto.TargetServerDto;
import lamp.server.aladin.core.service.TargetServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RequestMapping("/target-server")
@Controller
public class TargetServerController {

	@Autowired
	private TargetServerService targetServerService;

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

}
