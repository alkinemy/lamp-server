package lamp.admin.web.resource.controller;

import lamp.admin.LampAdminConstants;

import lamp.admin.core.app.simple.resource.AppResourceType;

import lamp.admin.domain.resource.repo.model.AppRepoType;
import lamp.admin.domain.resource.repo.model.dto.AppRepoDto;
import lamp.admin.domain.resource.repo.model.dto.LocalAppFileDto;
import lamp.admin.domain.resource.repo.model.form.LocalAppFileUploadForm;
import lamp.admin.domain.resource.repo.service.AppRepoService;
import lamp.admin.domain.resource.repo.service.LocalAppFileService;
import lamp.admin.domain.resource.repo.service.param.LocalAppFileSearchParams;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.FlashMessage;
import lamp.admin.web.support.annotation.MenuMapping;
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
@MenuMapping(MenuConstants.LOCAL_APP_FILE)
@Controller
@RequestMapping("/resource/file/LOCAL")
public class LocalAppFileController {

	@Autowired
	private LocalAppFileService localAppFileService;
	@Autowired
	private AppRepoService appRepoService;

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String list(@ModelAttribute("searchParams") LocalAppFileSearchParams searchParams, Pageable pageable, Model model) {
		Page<LocalAppFileDto> page = localAppFileService.getLocalAppFileList(searchParams, pageable);
		model.addAttribute("page", page);
		return "resource/file/local/list";
	}

	@RequestMapping(path = "/create", method = RequestMethod.GET)
	public String create(@ModelAttribute("editForm") LocalAppFileUploadForm editForm, Model model) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_CREATE);
		if (editForm.getRefId() != null) {
			LocalAppFileDto localAppFileDto = localAppFileService.getLocalAppFileDto(editForm.getRefId());
			editForm.setName(localAppFileDto.getName());
			editForm.setDescription(localAppFileDto.getDescription());
			editForm.setGroupId(localAppFileDto.getGroupId());
			editForm.setArtifactId(localAppFileDto.getArtifactId());
			editForm.setVersion(localAppFileDto.getBaseVersion());
		}

		List<AppRepoDto> appRepoList = appRepoService.getAppRepoListByType(AppRepoType.LOCAL);
		model.addAttribute("appRepositoryList", appRepoList);

		return "resource/file/local/edit";
	}

	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("editForm") LocalAppFileUploadForm editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return create(editForm, model);
		}

		localAppFileService.uploadLocalAppFile(editForm.getRepositoryId(), editForm);
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));

		return "redirect:/resource/file/LOCAL";
	}

}
