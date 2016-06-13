package lamp.admin.web.host.controller;

import lamp.admin.core.app.base.AppInstance;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.agent.service.AgentService;
import lamp.admin.domain.app.base.service.AppInstanceService;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.annotation.MenuMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Slf4j
@MenuMapping(MenuConstants.AGENT_APP)
@Controller
@RequestMapping("/agents/{agentId}")
public class AgentAppController {

	@Autowired
	private AgentService agentService;

	@Autowired
	private AppInstanceService appInstanceService;

	@RequestMapping(path = "/apps", method = RequestMethod.GET)
	public String list(Model model, @ModelAttribute("agent") Agent agent) {
		List<AppInstance> appInstances = appInstanceService.getAppInstancesByAgent(agent);
		model.addAttribute("appInstances", appInstances);
		return "hosts/agents/apps/list";
	}


	@ModelAttribute("agent")
	protected Agent getAgent(@PathVariable("agentId") String agentId) {
		return agentService.getAgent(agentId);
	}

}
