package lamp.server.aladin.admin.controller;

import lamp.server.aladin.LampConstants;
import lamp.server.aladin.admin.MenuConstants;
import lamp.server.aladin.admin.support.annotation.MenuMapping;
import lamp.server.aladin.core.dto.AgentDto;
import lamp.server.aladin.core.dto.AppDto;
import lamp.server.aladin.core.dto.AppRegisterForm;
import lamp.server.aladin.core.dto.AppTemplateDto;
import lamp.server.aladin.core.service.AgentService;
import lamp.server.aladin.core.service.AppService;
import lamp.server.aladin.core.service.AppTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@MenuMapping(MenuConstants.AGENT)
@Controller
@RequestMapping("/agent/{agentId}")
public class AppController {

	@Autowired
	private AgentService agentService;

	@Autowired
	private AppService appService;

	@Autowired
	private AppTemplateService appTemplateService;

	@RequestMapping(path = "/app", method = RequestMethod.GET)
	public String list(@PathVariable("agentId") String agentId, Model model, Pageable pageable) {
		List<AppDto> appList = appService.getAppList(agentId);
		model.addAttribute("appList", appList);
		return "agent/app/list";
	}

	@RequestMapping(path = "/app/create", method = RequestMethod.GET)
	public String createForm(@PathVariable("agentId") String agentId, @ModelAttribute("editForm") AppRegisterForm editForm, Model model) {
		model.addAttribute("action", LampConstants.ACTION_CREATE);

		// TODO Popup이나 Wizard 형식으로 분리하기
		List<AppTemplateDto> appTemplateList = appTemplateService.getAppTemplatesitoryList();
		model.addAttribute("appTemplateList", appTemplateList);

		return "agent/app/edit";
	}

	@RequestMapping(path = "/app/create", method = RequestMethod.POST)
	public String create(@PathVariable("agentId") String agentId, @Valid @ModelAttribute("editForm") AppRegisterForm editForm,
			BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return createForm(agentId, editForm, model);
		}
		appService.registerApp(agentId, editForm);

		redirectAttributes.addFlashAttribute("flashMessage", "성공적으로 등록하였습니다.");

		return "redirect:/agent/{agentId}/app";
	}

	@ModelAttribute("agent")
	protected AgentDto getAgent(@PathVariable("agentId") String agentId) {
		return agentService.getAgentDto(agentId);
	}

}
