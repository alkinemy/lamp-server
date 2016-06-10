package lamp.admin.web.host.controller;

import lamp.admin.domain.agent.model.AgentEventDto;
import lamp.admin.domain.agent.service.AgentEventService;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.annotation.MenuMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@MenuMapping(MenuConstants.AGENT_EVENT)
@Controller
@RequestMapping("/server/agent/event")
public class AgentEventController {

	@Autowired
	private AgentEventService agentEventService;

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String list(Model model, @SortDefault(sort = {"agentInstanceId", "agentInstanceEventSequence"}, direction = Sort.Direction.DESC) Pageable pageable) {
		Page<AgentEventDto> page = agentEventService.getAgentEventDtoList(pageable);
		model.addAttribute("page", page);
		return "server/agent/event/list";
	}


}
