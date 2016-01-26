package lamp.server.aladin.admin.controller;

import lamp.server.aladin.LampConstants;
import lamp.server.aladin.admin.AdminErrorCode;
import lamp.server.aladin.admin.MenuConstants;
import lamp.server.aladin.admin.support.FlashMessage;
import lamp.server.aladin.admin.support.annotation.MenuMapping;
import lamp.server.aladin.core.domain.AppRepo;
import lamp.server.aladin.core.dto.LocalAppFileDto;
import lamp.server.aladin.core.dto.LocalAppFileUploadForm;
import lamp.server.aladin.core.service.AppRepoService;
import lamp.server.aladin.core.service.LocalAppFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@MenuMapping(MenuConstants.APP_REPO)
@Controller
@RequestMapping(value = "/app-repository/LOCAL/{id}")
public class LocalAppRepoFileController {

	@Autowired
	private AppRepoService appRepoService;

	@Autowired
	private LocalAppFileService localAppFileService;

	@RequestMapping(path = "/file", method = RequestMethod.GET)
	public String files(@PathVariable("id") Long id, Pageable pageable, Model model) {
		Page<LocalAppFileDto> page = localAppFileService.getLocalAppFileList(id, pageable);
		model.addAttribute("page", page);
		return "app-repository/local/file/list";
	}

	@RequestMapping(path = "/file/create", method = RequestMethod.GET)
	public String editForm(@PathVariable("id") Long id,
			@ModelAttribute("editForm") LocalAppFileUploadForm editForm, Model model) {
		model.addAttribute("action", "create");
		return "app-repository/local/file/edit";
	}

	@RequestMapping(path = "/file/create", method = RequestMethod.POST)
	public String create(@PathVariable("id") Long id,
			@Valid @ModelAttribute("editForm") LocalAppFileUploadForm editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		MultipartFile uploadFile = editForm.getUploadFile();
		if (uploadFile == null || uploadFile.getSize() == 0) {
			bindingResult.rejectValue("uploadFile", "LocalAppFileUploadForm.uploadFile", "업로드할 파일을 선택해주세요.");
		}

		if (bindingResult.hasErrors()) {
			return editForm(id, editForm, model);
		}

		localAppFileService.uploadLocalAppFile(id, editForm);
		redirectAttributes.addFlashAttribute(LampConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));

		return "redirect:/app-repository/LOCAL/{id}/file ";
	}

	@ModelAttribute("appRepository")
	protected AppRepo getAppRepository(@PathVariable("id") Long id) {
		return appRepoService.getAppRepository(id);
	}

}
