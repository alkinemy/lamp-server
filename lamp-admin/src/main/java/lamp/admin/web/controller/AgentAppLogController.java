package lamp.admin.web.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.core.agent.domain.AgentDto;
import lamp.admin.core.agent.service.AgentService;
import lamp.admin.core.app.AppManagementListener;
import lamp.admin.core.app.domain.*;
import lamp.admin.core.app.service.AppFacadeService;
import lamp.admin.core.app.service.AppManagementListenerService;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

@Slf4j
@MenuMapping(MenuConstants.AGENT_APP)
@Controller
@RequestMapping("/agent/{agentId}")
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
		return "agent/app/log/list";
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
