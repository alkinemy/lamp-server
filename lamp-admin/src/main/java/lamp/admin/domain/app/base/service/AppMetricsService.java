package lamp.admin.domain.app.base.service;

import lamp.admin.core.app.base.AppInstance;
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
		endpoint.setAddress(appInstance.getHost().getAddress());
		metricsTarget.setEndpoint(endpoint);
		Map<String, String> tags = new HashMap<>();
		tags.put("appId", appInstance.getAppId());
		tags.put("appVersion", appInstance.getAppVersion());
		tags.put("hostId", appInstance.getHostId());

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
