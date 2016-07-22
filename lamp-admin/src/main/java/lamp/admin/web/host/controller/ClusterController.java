package lamp.admin.web.host.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.core.host.Cluster;
import lamp.admin.core.host.Host;
import lamp.admin.core.host.ScannedHost;

import lamp.admin.domain.base.exception.MessageException;
import lamp.admin.domain.host.model.AgentInstallResult;
import lamp.admin.domain.host.service.ClusterService;
import lamp.admin.domain.host.service.HostService;
import lamp.admin.domain.host.service.form.ClusterForm;
import lamp.admin.domain.host.service.form.HostScanForm;
import lamp.admin.domain.host.service.form.ManagedHostCredentialsForm;
import lamp.admin.domain.host.service.form.ScannedHostCredentialsForm;
import lamp.admin.domain.resource.repo.model.dto.AppRepoDto;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.annotation.MenuMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@MenuMapping(MenuConstants.CLUSTERS)
@Controller
@RequestMapping("/clusters")
public class ClusterController {

	@Autowired
	private ClusterService clusterService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		List<Cluster> clusters = clusterService.getClusterList();
		model.addAttribute("clusters", clusters);
		return "clusters/list";
	}


	@RequestMapping(path = "/**", method = RequestMethod.GET)
	public String create(Model model,
						 @ModelAttribute("editForm") ClusterForm editForm) {
		return createForm(model, editForm);
	}

	protected String createForm(Model model, ClusterForm editForm) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_CREATE);
		return "clusters/edit";
	}

	@RequestMapping(path = "/**", method = RequestMethod.POST)
	public String create(Model model,
						 @ModelAttribute("editForm") ClusterForm editForm,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {
		try {
			clusterService.addCluster(editForm);
			return "redirect:/clusters";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return createForm(model, editForm);
		}
	}

}
