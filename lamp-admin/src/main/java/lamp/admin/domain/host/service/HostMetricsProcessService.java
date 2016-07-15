package lamp.admin.domain.host.service;

import com.google.common.collect.Lists;
import lamp.admin.core.host.Host;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.agent.service.AgentService;
import lamp.collector.core.Endpoint;
import lamp.collector.metrics.exporter.TargetMetricsExporter;
import lamp.collector.metrics.processor.TargetMetricsProcessor;
import lamp.common.utils.CollectionUtils;
import lamp.monitoring.metrics.MonitoringMetricsTarget;
import lamp.monitoring.metrics.MonitoringTargetMetrics;
import lamp.monitoring.metrics.loader.MonitoringTargetMetricsHttpLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class HostMetricsProcessService {

	@Autowired
	private AgentService agentService;

	@Autowired
	private HostMetricsAlertProcessor hostMetricsAlertProcessor;

	@Autowired
	private HostStatusUpdateProcessor hostStatusUpdateProcessor;

	@Autowired(required = false)
	private List<TargetMetricsExporter> metricsExporters;

	private MonitoringTargetMetricsHttpLoader monitoringTargetMetricsHttpLoader;

	private List<TargetMetricsProcessor> targetMetricsProcessors;

	@PostConstruct
	public void init() {
		monitoringTargetMetricsHttpLoader = new MonitoringTargetMetricsHttpLoader();

		targetMetricsProcessors = Lists.newArrayList(hostMetricsAlertProcessor, hostStatusUpdateProcessor);
		if (CollectionUtils.isNotEmpty(metricsExporters)) {
			targetMetricsProcessors.addAll(metricsExporters);
		}
	}


	@Async
	public void processMetrics(Host host) {
		Optional<Agent> agentOptional = agentService.getAgentOptional(host.getId());

		MonitoringTargetMetrics targetMetrics = getTargetMetrics(host, agentOptional.orElse(null));

		targetMetricsProcessors.stream().forEach(targetMetricsProcessor -> targetMetricsProcessor.process(targetMetrics));

	}


	protected MonitoringTargetMetrics getTargetMetrics(Host host, Agent agent) {
		Map<String, String> tags = new LinkedHashMap<>();
		tags.put("targetType", "host");
		tags.put("hostId", host.getId());
		tags.put("hostName", host.getName());
		tags.put("clusterId", host.getClusterId());
		if (host.getTags() != null) {
			tags.putAll(host.getTags());
		}

		Endpoint endpoint = null;
		if (agent != null) {
			endpoint = new Endpoint();
			endpoint.setProtocol(agent.getProtocol());
			endpoint.setAddress(agent.getAddress());
			endpoint.setPort(agent.getPort());
			endpoint.setPath(agent.getMetricsPath());
		}

		MonitoringMetricsTarget target = new MonitoringMetricsTarget();
		target.setId(host.getId());
		target.setEndpoint(endpoint);
		target.setTags(tags);
		target.setTenantId(host.getTenantId());

		try {
			return monitoringTargetMetricsHttpLoader.getMetrics(target);
		} catch (Exception e) {
			log.error("Host metrics load failed", e);
			return new MonitoringTargetMetrics(target, e);
		}
	}



}
