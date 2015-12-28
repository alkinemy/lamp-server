package lamp.server.aladin.api.controller;

import lamp.server.aladin.api.dto.AgentForm;
import lamp.server.aladin.core.service.AgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/agent")
public class AgentApiController {

	@Autowired
	private AgentService agentService;

	@RequestMapping(path = "", method = RequestMethod.POST)
	public String register(AgentForm form) {
		agentService.insert(form);
		return "index";
	}

}
