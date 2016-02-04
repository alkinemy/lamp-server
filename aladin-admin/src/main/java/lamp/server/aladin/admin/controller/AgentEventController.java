package lamp.server.aladin.admin.controller;

import lamp.server.aladin.admin.MenuConstants;
import lamp.server.aladin.admin.support.annotation.MenuMapping;
import lamp.server.aladin.core.dto.AgentDto;
import lamp.server.aladin.core.dto.AgentEventDto;
import lamp.server.aladin.core.service.AgentEventService;
import lamp.server.aladin.core.service.AgentService;
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
@RequestMapping("/agent/event")
public class AgentEventController {

	@Autowired
	private AgentEventService agentEventService;

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String list(Model model, @SortDefault(sort = {"agentInstanceId", "agentInstanceEventSequence"}, direction = Sort.Direction.DESC) Pageable pageable) {
		Page<AgentEventDto> page = agentEventService.getAgentEventDtoList(pageable);
		model.addAttribute("page", page);
		return "agent/event/list";
	}


}
