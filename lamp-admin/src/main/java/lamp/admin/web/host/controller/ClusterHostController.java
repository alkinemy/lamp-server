package lamp.admin.web.host.controller;

import lamp.admin.LampAdminConstants;
import lamp.admin.core.host.*;
import lamp.admin.domain.base.exception.MessageException;
import lamp.admin.domain.host.service.HostFacadeService;
import lamp.admin.domain.host.model.form.AwsEc2HostsForm;
import lamp.admin.web.MenuConstants;
import lamp.admin.web.account.model.MenuItem;
import lamp.admin.web.support.annotation.MenuMapping;
import lamp.common.utils.VariableReplaceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@MenuMapping(MenuConstants.CLUSTERS)
@Controller
@RequestMapping("/clusters/{clusterId}/hosts")
public class ClusterHostController {

	@Autowired
	private HostFacadeService hostFacadeService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, @PathVariable("clusterId") String clusterId) {
		Cluster cluster = hostFacadeService.getCluster(clusterId);
		boolean isAws = ClusterType.AWS.equals(cluster.getType());
		List<Host> hosts = hostFacadeService.getHostsByClusterId(clusterId);
		model.addAttribute("hosts", hosts);
		if (isAws) {
			String region = "ap-northeast-2";

			Map<String, String> managementUrls = new HashMap<>();
			String urlTemplate = "https://{region}.console.aws.amazon.com/ec2/v2/home?region={region}#Instances:instanceId={instanceId};sort=Name";
			for (Host host : hosts) {
				if (host instanceof AwsEc2Host) {
					((AwsEc2Host)host).setRegion(region);
					String url = VariableReplaceUtils.replaceVariables(urlTemplate, host);
					managementUrls.put(((AwsEc2Host) host).getInstanceId(), url);
				}
			}
			model.addAttribute("managementUrls", managementUrls);
		}

		model.addAttribute("breadcrumb", getBreadcrumb(cluster));

		return isAws ? "clusters/hosts/aws-list" : "clusters/hosts/list";
	}

	protected List<MenuItem> getBreadcrumb(Cluster cluster) {
		List<MenuItem> breadcrumb = new ArrayList<>();
		breadcrumb.add(MenuItem.of(MenuConstants.CLUSTERS, cluster.getName(), "/clusters/" + cluster.getId() + "/hosts"));
		return breadcrumb;
	}

	@RequestMapping(path = "/add", method = RequestMethod.GET)
	public String add(Model model,
					  @PathVariable("clusterId") String clusterId,
					  @ModelAttribute("editForm") AwsEc2HostsForm editForm) {
		AwsCluster cluster = hostFacadeService.getAwsCluster(clusterId);
		BeanUtils.copyProperties(cluster, editForm);
		return addForm(model, clusterId, editForm);
	}

	protected String addForm(Model model, String clusterId, AwsEc2HostsForm editForm) {
		model.addAttribute(LampAdminConstants.ACTION_KEY, LampAdminConstants.ACTION_CREATE);
		return "clusters/hosts/edit";
	}

	@RequestMapping(path = "/add", method = RequestMethod.POST)
	public String add(Model model,
					  @PathVariable("clusterId") String clusterId,
					  @ModelAttribute("editForm") AwsEc2HostsForm editForm,
					  BindingResult bindingResult,
					  RedirectAttributes redirectAttributes) {
		try {
			hostFacadeService.addHosts(clusterId, editForm);
			return "redirect:/clusters/{clusterId}/hosts";
		} catch (MessageException e) {
			bindingResult.reject(e.getCode(), e.getArgs(), e.getMessage());
			return addForm(model, clusterId, editForm);
		}
	}

	@RequestMapping(path = "/{hostId}/delete", method = RequestMethod.GET)
	public String delete(Model model,
					  @PathVariable("hostId") String hostId) {
		hostFacadeService.deleteHost(hostId);
		return "redirect:/clusters/{clusterId}/hosts";
	}

}
