package lamp.admin.web.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.core.agent.domain.AgentDto;
import lamp.admin.core.agent.service.AgentService;
import lamp.admin.core.app.domain.AppDto;
import lamp.admin.core.app.domain.AppRegisterForm;
import lamp.admin.core.app.domain.AppTemplateDto;
import lamp.admin.core.app.service.AppFacadeService;
import lamp.admin.core.app.service.AppTemplateService;
import lamp.admin.core.base.exception.MessageException;
import lamp.admin.web.AdminErrorCode;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.FlashMessage;
import lamp.admin.web.support.annotation.MenuMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@MenuMapping(MenuConstants.AGENT_APP)
@Controller
@RequestMapping("/agent/{agentId}")
public class AgentAppController {

	@Autowired
	private AgentService agentService;

	@Autowired
	private AppFacadeService appFacadeService;

	@Autowired
	private AppTemplateService appTemplateService;

	@RequestMapping(path = "/app", method = RequestMethod.GET)
	public String list(@PathVariable("agentId") String agentId, Model model) {
		List<AppDto> appList = appFacadeService.getAppDtoList(agentId);
		model.addAttribute("appList", appList);
		return "agent/app/list";
	}

	@RequestMapping(path = "/app/create", method = RequestMethod.GET)
	public String createForm(@PathVariable("agentId") String agentId, @ModelAttribute("editForm") AppRegisterForm editForm, Model model) {
		model.addAttribute("action", LampAdminConstants.ACTION_CREATE);

		// TODO Popup이나 Wizard 형식으로 분리하기
		List<AppTemplateDto> appTemplateList = appTemplateService.getAppTemplateDtoList();
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

		try {
			appFacadeService.registerApp(agentId, editForm);

			redirectAttributes.addFlashAttribute(LampAdminConstants.FLASH_MESSAGE_KEY, FlashMessage.ofSuccess(AdminErrorCode.INSERT_SUCCESS));

			return "redirect:/agent/{agentId}/app";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return createForm(agentId, editForm, model);
		}

	}

	@RequestMapping(path = "/app/{appId}/start", method = RequestMethod.GET)
	public String start(@PathVariable("agentId") String agentId,
			@PathVariable("appId") String appId, RedirectAttributes redirectAttributes) {
		appFacadeService.startApp(agentId, appId);

		return "redirect:/agent/{agentId}/app";
	}

	@RequestMapping(path = "/app/{appId}/stop", method = RequestMethod.GET)
	public String stop(@PathVariable("agentId") String agentId,
			@PathVariable("appId") String appId, RedirectAttributes redirectAttributes) {
		appFacadeService.stopApp(agentId, appId);

		return "redirect:/agent/{agentId}/app";
	}

	@RequestMapping(path = "/app/{appId}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable("agentId") String agentId,
			@PathVariable("appId") String appId, RedirectAttributes redirectAttributes) {
		appFacadeService.deregisterApp(agentId, appId);

		return "redirect:/agent/{agentId}/app";
	}

	@ModelAttribute("agent")
	protected AgentDto getAgent(@PathVariable("agentId") String agentId) {
		return agentService.getAgentDto(agentId);
	}

}
