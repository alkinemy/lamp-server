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
import lamp.server.aladin.utils.FilenameUtils;
import lamp.server.aladin.utils.StringUtils;
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
import java.nio.file.Files;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

@Slf4j
@MenuMapping(MenuConstants.APP_REPO)
@Controller
@RequestMapping(value = "/app/repository/LOCAL/{id}")
public class LocalAppRepoFileController {

	@Autowired
	private AppRepoService appRepoService;

	@Autowired
	private LocalAppFileService localAppFileService;

	@RequestMapping(path = "/file", method = RequestMethod.GET)
	public String files(@PathVariable("id") Long id, Pageable pageable, Model model) {
		Page<LocalAppFileDto> page = localAppFileService.getLocalAppFileList(id, pageable);
		model.addAttribute("page", page);
		return "app/repository/local/file/list";
	}

	@RequestMapping(path = "/file/create", method = RequestMethod.GET)
	public String create(@PathVariable("id") Long id,
			@ModelAttribute("editForm") LocalAppFileUploadForm editForm, Model model) {
		model.addAttribute(LampConstants.ACTION_KEY, LampConstants.ACTION_CREATE);
		return "app/repository/local/file/edit";
	}

	@RequestMapping(path = "/file/create", method = RequestMethod.POST)
	public String create(@PathVariable("id") Long id,
			@Valid @ModelAttribute("editForm") LocalAppFileUploadForm editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return create(id, editForm, model);
		}

		localAppFileService.uploadLocalAppFile(id, editForm);
		redirectAttributes.addFlashAttribute(LampConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));

		return "redirect:/app/repository/LOCAL/{id}/file ";
	}

	@ModelAttribute("appRepository")
	protected AppRepo getAppRepository(@PathVariable("id") Long id) {
		return appRepoService.getAppRepo(id);
	}

}
