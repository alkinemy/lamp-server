package lamp.server.aladin.admin.controller;

import lamp.server.aladin.LampConstants;
import lamp.server.aladin.admin.AdminErrorCode;
import lamp.server.aladin.admin.MenuConstants;
import lamp.server.aladin.admin.support.FlashMessage;
import lamp.server.aladin.admin.support.annotation.MenuMapping;
import lamp.server.aladin.core.domain.AppResourceType;
import lamp.server.aladin.core.dto.AppRepoDto;
import lamp.server.aladin.core.dto.LocalAppFileDto;
import lamp.server.aladin.core.dto.LocalAppFileSearchParams;
import lamp.server.aladin.core.dto.LocalAppFileUploadForm;
import lamp.server.aladin.core.service.AppRepoService;
import lamp.server.aladin.core.service.LocalAppFileService;
import lamp.server.aladin.utils.StringUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@MenuMapping(MenuConstants.LOCAL_APP_FILE)
@Controller
@RequestMapping("/app/file/LOCAL")
public class LocalAppFileController {

	@Autowired
	private LocalAppFileService localAppFileService;
	@Autowired
	private AppRepoService appRepoService;

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String list(@ModelAttribute("searchParams") LocalAppFileSearchParams searchParams, Pageable pageable, Model model) {
		Page<LocalAppFileDto> page = localAppFileService.getLocalAppFileList(searchParams, pageable);
		model.addAttribute("page", page);
		return "app/file/local/list";
	}

	@RequestMapping(path = "/create", method = RequestMethod.GET)
	public String create(@ModelAttribute("editForm") LocalAppFileUploadForm editForm, Model model) {
		model.addAttribute(LampConstants.ACTION_KEY, LampConstants.ACTION_CREATE);
		if (editForm.getRefId() != null) {
			LocalAppFileDto localAppFileDto = localAppFileService.getLocalAppFileDto(editForm.getRefId());
			editForm.setName(localAppFileDto.getName());
			editForm.setDescription(localAppFileDto.getDescription());
			editForm.setGroupId(localAppFileDto.getGroupId());
			editForm.setArtifactId(localAppFileDto.getArtifactId());
			editForm.setVersion(localAppFileDto.getBaseVersion());
		}

		List<AppRepoDto> appRepoList = appRepoService.getAppRepoListByType(AppResourceType.LOCAL);
		model.addAttribute("appRepositoryList", appRepoList);

		return "app/file/local/edit";
	}

	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("editForm") LocalAppFileUploadForm editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return create(editForm, model);
		}

		localAppFileService.uploadLocalAppFile(editForm.getRepositoryId(), editForm);
		redirectAttributes.addFlashAttribute(LampConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));

		return "redirect:/app/file/LOCAL";
	}

}
