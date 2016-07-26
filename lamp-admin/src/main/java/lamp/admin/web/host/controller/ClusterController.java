package lamp.admin.web.host.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.core.host.Cluster;

import lamp.admin.domain.base.exception.MessageException;
import lamp.admin.domain.host.model.SshKey;
import lamp.admin.domain.host.service.ClusterService;
import lamp.admin.domain.host.service.SshKeyService;
import lamp.admin.domain.host.service.form.ClusterForm;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.support.annotation.MenuMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

	@Autowired
	private SshKeyService sshKeyService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		List<Cluster> clusters = clusterService.getClusterList();
		model.addAttribute("clusters", clusters);
		return "clusters/list";
	}

	@RequestMapping(path = "/add", method = RequestMethod.GET)
	public String add(Model model,
						 @ModelAttribute("editForm") ClusterForm editForm) {
		return addForm(model, editForm);
	}

	protected String addForm(Model model, ClusterForm editForm) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_CREATE);

		List<SshKey> sshKeys = sshKeyService.getSshKeyList();
		model.addAttribute("sshKeys", sshKeys);

		return "clusters/edit";
	}

	@RequestMapping(path = "/add", method = RequestMethod.POST)
	public String add(Model model,
						 @ModelAttribute("editForm") ClusterForm editForm,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {
		try {
			clusterService.addCluster(editForm);
			return "redirect:/clusters";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return addForm(model, editForm);
		}
	}

	@RequestMapping(path = "/{clusterId}/update", method = RequestMethod.GET)
	public String update(Model model,
						 @PathVariable("clusterId") String clusterId,
						 @ModelAttribute("editForm") ClusterForm editForm) {
		ClusterForm form = clusterService.getClusterForm(clusterId);
		BeanUtils.copyProperties(form, editForm);
		return updateForm(model, editForm);
	}

	protected String updateForm(Model model, ClusterForm editForm) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_UPDATE);

		List<SshKey> sshKeys = sshKeyService.getSshKeyList();
		model.addAttribute("sshKeys", sshKeys);

		return "clusters/edit";
	}

	@RequestMapping(path = "/{clusterId}/update", method = RequestMethod.POST)
	public String update(Model model,
						 @PathVariable("clusterId") String clusterId,
						 @ModelAttribute("editForm") ClusterForm editForm,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes) {
		try {
			clusterService.updateCluster(clusterId, editForm);
			return "redirect:/clusters";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return addForm(model, editForm);
		}
	}

}
