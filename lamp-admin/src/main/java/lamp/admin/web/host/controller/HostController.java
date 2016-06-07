package lamp.admin.web.host.controller;

import lamp.admin.core.host.Host;
import lamp.admin.core.host.ScannedHost;
import lamp.admin.domain.host.service.HostService;
import lamp.admin.domain.host.service.form.HostCredentialsForm;
import lamp.admin.domain.host.service.form.HostScanForm;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.annotation.MenuMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@MenuMapping(MenuConstants.HOSTS)
@Controller
@RequestMapping("/hosts")
public class HostController {

	@Autowired
	private HostService hostService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		List<Host> hosts = hostService.getHosts("");
		model.addAttribute("hosts", hosts);
		return "host/list";
	}

	@RequestMapping(path = "/add/scan", method = RequestMethod.GET)
	public String scan(Model model, @ModelAttribute("editForm") HostScanForm editForm) {
		return "host/scan";
	}

	@RequestMapping(path = "/add/scan", method = RequestMethod.POST)
	public String scan(Model model,
					   @ModelAttribute("editForm") HostScanForm editForm,
					   BindingResult bindingResult,
					   RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return scan(model, editForm);
		}

		List<ScannedHost> scannedHosts = hostService.scanHost(editForm);
		model.addAttribute("scannedHosts", scannedHosts);
		return scan(model, editForm);
	}

	@RequestMapping(path = "/add/credentials", method = RequestMethod.POST)
	public String credentials(Model model,
					   @ModelAttribute("editForm") HostCredentialsForm editForm,
					   BindingResult bindingResult,
					   RedirectAttributes redirectAttributes) {

		return "host/credentials";
	}

	@RequestMapping(path = "/add", method = RequestMethod.POST)
	public String add(Model model,
							  @ModelAttribute("editForm") HostCredentialsForm editForm,
							  BindingResult bindingResult,
							  RedirectAttributes redirectAttributes) throws Exception {

		log.info("editForm = {}", editForm);
		String clusterId = "1234";
		hostService.installAgents(clusterId, editForm);

		return "redirect:/hosts";
	}
//
//	protected String createForm(DockerApp editForm, Model model) {
//
//		List<String> networkList = new ArrayList<>();
//		networkList.add("host");
//		networkList.add("bridge");
//
//		model.addAttribute("networkList", networkList);
//
//		return "docker/app/edit";
//	}
//
//	@RequestMapping(path = "/create", method = RequestMethod.POST)
//	public String create(@ModelAttribute("editForm") DockerApp editForm,
//						 Model model,
//						 BindingResult bindingResult,
//						 RedirectAttributes redirectAttributes) {
//
//		List<Parameter> parameters = editForm.getContainer()
//			.getParameters().stream().filter(p -> StringUtils.isNotBlank(p.getKey()) && StringUtils.isNotBlank(p.getValue())).collect(Collectors.toList());
//		editForm.getContainer().setParameters(parameters);
//
//		List<PortMapping> portMappings = editForm.getContainer()
//			.getPortMappings().stream().filter(p -> p.getContainerPort() != null || p.getHostPort() != null).collect(Collectors.toList());
//		editForm.getContainer().setPortMappings(portMappings);
//
//		List<Volume> volumes = editForm.getContainer()
//			.getVolumes().stream().filter(p -> StringUtils.isNotBlank(p.getContainerPath()) || StringUtils.isNotBlank(p.getHostPath())).collect(Collectors.toList());
//		editForm.getContainer().setVolumes(volumes);
//
//		try {
//			dockerAppService.createDockerApp(editForm);
//			return "redirect:/docker/apps";
//		} catch (MessageException e) {
//			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
//			return createForm(editForm, model);
//		}
//	}

}
