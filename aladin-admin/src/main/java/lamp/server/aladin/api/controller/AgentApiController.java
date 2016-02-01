package lamp.server.aladin.api.controller;

import lamp.server.aladin.core.dto.AgentDto;
import lamp.server.aladin.api.dto.AgentEventForm;
import lamp.server.aladin.api.dto.AgentRegisterForm;
import lamp.server.aladin.api.service.AgentFacadeService;
import lamp.server.aladin.core.service.AgentEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/agent")
public class AgentApiController {

	@Autowired
	private AgentFacadeService agentFacadeService;

	@Autowired
	private AgentEventService agentEventService;

	@RequestMapping(path = "", method = RequestMethod.POST)
	public AgentDto register(@Valid @RequestBody AgentRegisterForm form) {
		return agentFacadeService.register(form);
	}

	@RequestMapping(path = "/{id:.+}", method = RequestMethod.DELETE)
	public void deregister(@PathVariable("id") String id, @AuthenticationPrincipal User user) {
		log.info("user = {}", user);
		agentFacadeService.deregister(id);
	}

	@RequestMapping(path = "/{id}/event", method = RequestMethod.POST)
	public void event(@PathVariable("id") String id, @Valid @RequestBody AgentEventForm form) {
		// TODO 구현바람
		log.info("event = {}", form);
		agentEventService.insertAgentEvent(id, form);
	}

}
