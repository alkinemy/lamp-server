package lamp.server.aladin.api.controller;

import lamp.server.aladin.api.dto.AgentDto;
import lamp.server.aladin.api.dto.AgentRegisterForm;
import lamp.server.aladin.api.service.AgentFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/agent")
public class AgentApiController {

	@Autowired
	private AgentFacadeService agentFacadeService;

	@RequestMapping(path = "", method = RequestMethod.POST)
	public AgentDto register(@Valid @RequestBody AgentRegisterForm form) {
		return agentFacadeService.insert(form);
	}

}
