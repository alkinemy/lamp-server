package lamp.server.aladin.admin.controller;

import lamp.server.aladin.core.dto.AppRepoCreateForm;
import lamp.server.aladin.core.service.AppRepoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
public abstract class GenericAppRepoController<CF extends AppRepoCreateForm> {

	@Autowired
	private AppRepoService appRepoService;

	protected abstract String getCreateViewName();

	@RequestMapping(path = "/create", method = RequestMethod.GET)
	public String createForm(@ModelAttribute("editForm") CF editForm, Model model) {
		model.addAttribute("action", "create");
		return getCreateViewName();
	}

	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("editForm") CF editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		log.info("repositoryType=LOCAL");
		if (bindingResult.hasErrors()) {
			return createForm(editForm, model);
		}
		appRepoService.insertAppRepository(editForm);
		redirectAttributes.addFlashAttribute("flashMessage", "성공적으로 등록하였습니다.");


		return "redirect:/app-repository";
	}


}
