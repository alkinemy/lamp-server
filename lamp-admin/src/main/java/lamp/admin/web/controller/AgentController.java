package lamp.admin.web.controller;

import lamp.admin.core.agent.domain.AgentDto;
import lamp.admin.core.agent.service.AgentService;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.annotation.MenuMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@MenuMapping(MenuConstants.AVAILABLE_AGENT)
@Controller
@RequestMapping("/agent")
public class AgentController {

	@Autowired
	private AgentService agentService;

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String list(Model model, Pageable pageable) {
		Page<AgentDto> page = agentService.getAgentDtoList(pageable);
		model.addAttribute("page", page);
		return "agent/list";
	}


}
