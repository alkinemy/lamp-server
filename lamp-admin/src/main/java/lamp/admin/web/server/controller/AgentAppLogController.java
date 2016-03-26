package lamp.admin.web.server.controller;

import lamp.admin.domain.agent.model.AgentDto;
import lamp.admin.domain.agent.service.AgentService;
import lamp.admin.domain.app.model.AppDto;
import lamp.admin.domain.app.model.LogFile;
import lamp.admin.domain.app.service.AppFacadeService;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@MenuMapping(MenuConstants.AGENT_APP)
@Controller
@RequestMapping("/server/agent/{agentId}")
public class AgentAppLogController {

	@Autowired
	private AgentService agentService;

	@Autowired
	private AppFacadeService appFacadeService;

	@RequestMapping(path = "/app/{appId}/log", method = RequestMethod.GET)
	public String logFiles(@PathVariable("agentId") String agentId,
					   @PathVariable("appId") String appId,
					   Model model) {
		AppDto appDto = appFacadeService.getAppDto(agentId, appId);
		model.addAttribute("app", appDto);

		List<LogFile> logFiles = appFacadeService.getLogFiles(agentId, appId);
		model.addAttribute("logFiles", logFiles);
		return "server/agent/app/log/list";
	}

	@RequestMapping(path = "/app/{appId}/log/{filename:.+}", method = RequestMethod.GET)
	public void logFile(@PathVariable("agentId") String agentId,
			@PathVariable("appId") String appId,
			@PathVariable("filename") String filename,
			HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(filename,"UTF-8")+"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

		appFacadeService.transferLogFile(agentId, appId, filename, response.getOutputStream());
	}

	@ModelAttribute("agent")
	protected AgentDto getAgent(@PathVariable("agentId") String agentId) {
		return agentService.getAgentDto(agentId);
	}

}
