package lamp.admin.domain.app.base.service;

import lamp.admin.core.app.base.AppInstance;
import lamp.admin.core.host.Host;
import lamp.admin.domain.host.service.HostService;
import lamp.collector.core.base.Endpoint;
import lamp.collector.core.metrics.MetricsTarget;
import lamp.collector.core.metrics.MetricsTargetProvider;
import lamp.collector.core.metrics.TargetMetrics;
import lamp.collector.core.metrics.loader.TargetMetricsLoader;
import lamp.collector.core.metrics.loader.http.TargetMetricsHttpLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppMetricsService implements MetricsTargetProvider, TargetMetricsLoader {

	@Autowired
	private AppInstanceService appInstanceService;

	private TargetMetricsHttpLoader targetMetricsHttpLoader = new TargetMetricsHttpLoader();

	@Override public List<MetricsTarget> getMetricsTargets() {
		List<AppInstance> appInstances = appInstanceService.getAppInstances();

		return appInstances
			.stream()
			.filter(i -> i.isMetricsEndpointEnabled())
			.map(this::newMetricsTarget).collect(Collectors.toList());
	}

	protected MetricsTarget newMetricsTarget(AppInstance appInstance) {
		MetricsTarget metricsTarget = new MetricsTarget();
		metricsTarget.setId(appInstance.getId());
		Endpoint endpoint = new Endpoint(appInstance.getMetricsEndpoint());
		endpoint.setAddress(appInstance.getHostAddress());
		metricsTarget.setEndpoint(endpoint);

		Map<String, String> tags = new HashMap<>();
		tags.put("appId", appInstance.getAppId());
		tags.put("appName", appInstance.getAppName());
		tags.put("appInstanceId", appInstance.getId());
//		tags.put("appVersion", appInstance.getAppVersion()); //  value may contain any character except colon ':', and equals '='.
		tags.put("clusterId", appInstance.getClusterId());
		tags.put("clusterName", appInstance.getClusterName());
		tags.put("hostId", appInstance.getHostId());
		tags.put("hostName", appInstance.getHostName());
		tags.put("hostAddress", appInstance.getHostAddress());

		if (appInstance.getTags() != null) {
			tags.putAll(appInstance.getTags());
		}
		metricsTarget.setTags(tags);
		return metricsTarget;
	}

	@Override public TargetMetrics getMetrics(MetricsTarget target) {
		return targetMetricsHttpLoader.getMetrics(target);
	}

}
