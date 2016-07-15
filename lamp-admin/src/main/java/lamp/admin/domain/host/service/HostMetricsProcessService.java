package lamp.admin.domain.host.service;

import com.google.common.collect.Lists;
import lamp.admin.core.host.Host;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.agent.service.AgentService;
import lamp.collector.core.base.Endpoint;
import lamp.collector.core.metrics.handler.TargetMetricsHandler;
import lamp.collector.core.metrics.handler.exporter.TargetMetricsExporter;
import lamp.collector.core.metrics.processor.MetricsTargetProcessor;
import lamp.collector.core.metrics.processor.TargetMetricsProcessor;
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
	private HostMetricsAlertHandler hostMetricsAlertProcessor;

	@Autowired
	private HostStatusUpdateHandler hostStatusUpdateProcessor;

	@Autowired(required = false)
	private List<TargetMetricsExporter> metricsExporters;

	private MetricsTargetProcessor<MonitoringMetricsTarget, MonitoringTargetMetrics> metricsTargetProcessor;

	@PostConstruct
	public void init() {
		MonitoringTargetMetricsHttpLoader monitoringTargetMetricsHttpLoader = new MonitoringTargetMetricsHttpLoader();

		List<TargetMetricsHandler> targetMetricsHandlers = Lists.newArrayList(hostMetricsAlertProcessor, hostStatusUpdateProcessor);
		if (CollectionUtils.isNotEmpty(metricsExporters)) {
			targetMetricsHandlers.addAll(metricsExporters);
		}

		TargetMetricsProcessor targetMetricsProcessor = new TargetMetricsProcessor(targetMetricsHandlers);

		metricsTargetProcessor = new MetricsTargetProcessor<>(monitoringTargetMetricsHttpLoader, targetMetricsProcessor);
	}


	@Async
	public void processMetrics(Host host) {
		Optional<Agent> agentOptional = agentService.getAgentOptional(host.getId());

		MonitoringMetricsTarget target = getMetricsTarget(host, agentOptional.orElse(null));
		metricsTargetProcessor.process(target);
	}

	protected MonitoringMetricsTarget getMetricsTarget(Host host, Agent agent) {
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

		return target;
	}



}
