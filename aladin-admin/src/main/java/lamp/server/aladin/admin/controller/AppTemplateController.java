package lamp.server.aladin.admin.controller;

import lamp.server.aladin.LampConstants;
import lamp.server.aladin.admin.MenuConstants;
import lamp.server.aladin.admin.support.annotation.MenuMapping;
import lamp.server.aladin.core.domain.AppResourceType;
import lamp.server.aladin.core.domain.AppRepo;
import lamp.server.aladin.core.dto.AppTemplateCreateForm;
import lamp.server.aladin.core.dto.AppTemplateDto;
import lamp.server.aladin.core.service.AppRepoService;
import lamp.server.aladin.core.service.AppTemplateService;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

@Slf4j
@MenuMapping(MenuConstants.APP_TEMPLATE)
@Controller
@RequestMapping("/app-template")
public class AppTemplateController {

	@Autowired
	private AppTemplateService appTemplateService;

	@Autowired
	private AppRepoService appRepoService;

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String list(Model model, Pageable pageable) {
		Page<AppTemplateDto> page = appTemplateService.getAppTemplatesitoryList(pageable);
		model.addAttribute("page", page);
		return "app-template/list";
	}

	@RequestMapping(path = "/create", method = RequestMethod.GET)
	public String createForm(@ModelAttribute("editForm") AppTemplateCreateForm editForm, Model model) {
		model.addAttribute("action", LampConstants.ACTION_CREATE);

		if (editForm.getRepositoryId() != null) {
			AppRepo appRepo = appRepoService.getAppRepository(editForm.getRepositoryId());
			model.addAttribute("appRepository", appRepo);
		}

		AppResourceType templateType = editForm.getTemplateType();
		List<AppRepo> appRepoList = appRepoService.getAppRepositoryListByType(templateType);
		model.addAttribute("appRepositoryList", appRepoList);

		return "app-template/edit";
	}

	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("editForm") AppTemplateCreateForm editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return createForm(editForm, model);
		}
		appTemplateService.insertAppTemplate(editForm);
		redirectAttributes.addFlashAttribute("flashMessage", "성공적으로 등록하였습니다.");


		return "redirect:/app-template";
	}

}
