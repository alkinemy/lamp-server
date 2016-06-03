package lamp.admin.api.controller;

import lamp.admin.api.service.AgentFacadeService;
import lamp.admin.api.util.HttpServletRequestUtils;
import lamp.admin.domain.agent.model.AgentDto;
import lamp.admin.domain.agent.model.AgentEventForm;
import lamp.admin.domain.agent.model.AgentRegisterForm;
import lamp.admin.domain.agent.service.AgentEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
	public AgentDto register(@Valid @RequestBody AgentRegisterForm form, HttpServletRequest request) {
		String address = HttpServletRequestUtils.getClientAddress(request);
		form.setAddress(address);
		return agentFacadeService.register(form);
	}

	@RequestMapping(path = "/{id:.+}", method = RequestMethod.DELETE)
	public void deregister(@PathVariable("id") String id, @AuthenticationPrincipal User user) {
		agentFacadeService.deregister(id);
	}

	@RequestMapping(path = "/{id}/event", method = RequestMethod.POST)
	public void event(@PathVariable("id") String id, @Valid @RequestBody AgentEventForm form) {
		agentEventService.insertAgentEvent(id, form);

		// TODO Event Push 구현바람
	}

}
