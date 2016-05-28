package lamp.admin.web.docker.controller;

import lamp.admin.domain.base.exception.MessageException;
import lamp.admin.domain.docker.model.DockerApp;
import lamp.admin.domain.docker.model.Parameter;
import lamp.admin.domain.docker.model.PortMapping;
import lamp.admin.domain.docker.model.Volume;
import lamp.admin.domain.docker.service.DockerAppService;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.annotation.MenuMapping;
import lamp.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@MenuMapping(MenuConstants.DOCKER_APPS)
@Controller
@RequestMapping("/docker/apps")
public class DockerAppController {

	@Autowired
	private DockerAppService dockerAppService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		List<DockerApp> dockerApps = dockerAppService.getDockerApps();
		model.addAttribute("dockerApps", dockerApps);
		return "docker/app/list";
	}

	@RequestMapping(path = "/create", method = RequestMethod.GET)
	public String create(@ModelAttribute("editForm") DockerApp editForm,
						 Model model) {


		return createForm(editForm, model);
	}

	protected String createForm(DockerApp editForm, Model model) {

		List<String> networkList = new ArrayList<>();
		networkList.add("host");
		networkList.add("bridge");

		model.addAttribute("networkList", networkList);

		return "docker/app/edit";
	}

	@RequestMapping(path = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute("editForm") DockerApp editForm,
						 Model model,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {

		List<Parameter> parameters = editForm.getContainer()
			.getParameters().stream().filter(p -> StringUtils.isNotBlank(p.getKey()) && StringUtils.isNotBlank(p.getValue())).collect(Collectors.toList());
		editForm.getContainer().setParameters(parameters);

		List<PortMapping> portMappings = editForm.getContainer()
			.getPortMappings().stream().filter(p -> p.getContainerPort() != null || p.getHostPort() != null).collect(Collectors.toList());
		editForm.getContainer().setPortMappings(portMappings);

		List<Volume> volumes = editForm.getContainer()
			.getVolumes().stream().filter(p -> StringUtils.isNotBlank(p.getContainerPath()) || StringUtils.isNotBlank(p.getHostPath())).collect(Collectors.toList());
		editForm.getContainer().setVolumes(volumes);

		try {
			dockerAppService.createDockerApp(editForm);
			return "redirect:/docker/apps";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return createForm(editForm, model);
		}
	}

}
