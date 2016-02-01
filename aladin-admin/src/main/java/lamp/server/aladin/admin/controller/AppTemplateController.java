package lamp.server.aladin.admin.controller;

import com.google.common.collect.Lists;
import lamp.server.aladin.LampConstants;
import lamp.server.aladin.admin.AdminErrorCode;
import lamp.server.aladin.admin.MenuConstants;
import lamp.server.aladin.admin.support.FlashMessage;
import lamp.server.aladin.admin.support.annotation.MenuMapping;
import lamp.server.aladin.core.domain.AppResourceType;
import lamp.server.aladin.core.domain.AppRepo;
import lamp.server.aladin.core.domain.CommandShell;
import lamp.server.aladin.core.dto.AppRepoDto;
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
import java.util.EnumSet;
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
	public String create(@ModelAttribute("editForm") AppTemplateCreateForm editForm, Model model) {

		editForm.setPidFile("${workDirectory}/${appId}.pid");

		return createForm(editForm, model);
	}

	protected String createForm(@ModelAttribute("editForm") AppTemplateCreateForm editForm, Model model) {
		model.addAttribute(LampConstants.ACTION_KEY, LampConstants.ACTION_CREATE);

		if (editForm.getRepositoryId() != null) {
			AppRepo appRepo = appRepoService.getAppRepository(editForm.getRepositoryId());
			model.addAttribute("appRepository", appRepo);
		}

		AppResourceType templateType = editForm.getResourceType();
		List<AppRepoDto> appRepoList;
		if (AppResourceType.NONE.equals(templateType)) {
			appRepoList = Lists.newArrayList(AppRepoDto.of(null, "사용안함", null, AppResourceType.NONE.name()));
		} else {
			appRepoList = appRepoService.getAppRepositoryListByType(templateType);
		}
		model.addAttribute("appRepositoryList", appRepoList);

		EnumSet<CommandShell> commandShellList = EnumSet.allOf(CommandShell.class);
		model.addAttribute("commandShellList", commandShellList);

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

		redirectAttributes.addFlashAttribute(LampConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));


		return "redirect:/app-template";
	}

}
