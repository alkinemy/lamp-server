package lamp.admin.web.monitoring.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.domain.base.exception.FlashMessageException;
import lamp.admin.domain.monitoring.model.WatchTargetCreateForm;
import lamp.admin.domain.monitoring.model.WatchTargetDto;
import lamp.admin.domain.monitoring.model.WatchTargetUpdateForm;
import lamp.admin.domain.monitoring.service.WatchTargetService;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.FlashMessage;
import lamp.admin.web.support.annotation.MenuMapping;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@MenuMapping(MenuConstants.MONITORING_WATCH_TARGET)
@Controller
@RequestMapping("/monitoring/watch-target")
public class WatchTargetController {

	@Autowired
	private WatchTargetService watchTargetService;


	@RequestMapping(path = "", method = RequestMethod.GET)
	public String list(Model model, @SortDefault(value = "name", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<WatchTargetDto> page = watchTargetService.getWatchTargetDtoList(pageable);
		model.addAttribute("page", page);
		return "monitoring/watch-target/list";
	}

	@RequestMapping(path = "/addHostEntity", method = RequestMethod.GET)
	public String createForm(@ModelAttribute("editForm") WatchTargetCreateForm editForm, Model model) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_CREATE);

		return "monitoring/watch-target/edit";
	}

	@RequestMapping(path = "/addHostEntity", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute("editForm") WatchTargetCreateForm editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return createForm(editForm, model);
		}
		watchTargetService.insertWatchTarget(editForm);
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));


		return "redirect:/monitoring/watch-target";
	}

	@RequestMapping(path = "/{id}/update", method = RequestMethod.GET)
	public String update(@PathVariable("id") String id, @ModelAttribute("editForm") WatchTargetUpdateForm editForm, Model model) {
		WatchTargetUpdateForm updateForm = watchTargetService.getWatchTargetUpdateForm(id);
		BeanUtils.copyProperties(updateForm, editForm);

		return updateForm(id, editForm, model);
	}

	protected String updateForm(String id, WatchTargetUpdateForm editForm, Model model) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_UPDATE);

		return "monitoring/watch-target/edit";
	}

	@RequestMapping(path = "/{id}/update", method = RequestMethod.POST)
	public String update(@PathVariable("id") String id, @ModelAttribute("editForm") WatchTargetUpdateForm editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return updateForm(id, editForm, model);
		}

		watchTargetService.updateWatchTarget(id, editForm);
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.UPDATE_SUCCESS));

		return "redirect:/monitoring/watch-target";
	}

	@RequestMapping(path = "/removeHostEntity", method = RequestMethod.GET)
	public String delete(@RequestParam("id") String id
			, RedirectAttributes redirectAttributes) {

		FlashMessage flashMessage;
		try {
			watchTargetService.deleteWatchTarget(id);
			flashMessage = FlashMessage.ofSuccess(AdminErrorCode.DELETE_SUCCESS);
		} catch (FlashMessageException e) {
			flashMessage = FlashMessage.ofError(e.getMessage(), e.getCode(), e.getArgs());
		}
		redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, flashMessage);

		return "redirect:/monitoring/watch-target";
	}


}
