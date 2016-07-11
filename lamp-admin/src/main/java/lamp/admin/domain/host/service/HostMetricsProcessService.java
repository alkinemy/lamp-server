package lamp.admin.domain.host.service;

import com.google.common.collect.Lists;
import lamp.admin.core.agent.AgentClient;
import lamp.admin.core.host.Host;
import lamp.admin.core.host.HostStatus;
import lamp.admin.domain.agent.model.Agent;
import lamp.admin.domain.agent.service.AgentService;
import lamp.admin.domain.host.model.HostStatusCode;
import lamp.admin.domain.host.model.entity.HostStatusEntity;
import lamp.admin.domain.monitoring.service.AlertEventService;
import lamp.collector.metrics.exporter.MetricsExporter;
import lamp.common.collector.model.TargetMetrics;
import lamp.common.monitoring.model.MonitoringTargetMetrics;
import lamp.common.utils.CollectionUtils;
import lamp.monitoring.core.alert.AlertMonitoringProcessor;
import lamp.monitoring.core.alert.AlertRuleProcessor;
import lamp.monitoring.core.alert.AlertRuleProvider;
import lamp.monitoring.core.alert.model.AlertRule;
import lamp.monitoring.core.metrics.model.SpelTargetMetricsAlertRule;
import lamp.monitoring.core.metrics.model.SpelTargetMetricsAlertRuleExpression;
import lamp.monitoring.core.metrics.service.SpelTargetMetricsAlertRuleProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@Service
public class HostMetricsProcessService {

	@Autowired
	private AgentService agentService;

	@Autowired
	private AgentClient agentClient;

	@Autowired
	private HostStatusEntityService hostStatusEntityService;

	@Autowired(required = false)
	private List<MetricsExporter> metricsExporters;

	@Autowired
	private AlertEventService alertEventService;

	private AlertMonitoringProcessor alertMonitoringProcessor;

	private List<AlertRuleProcessor> alertRuleProcessors;

	@PostConstruct
	public void init() {
		AlertRuleProvider alertRuleProvider = new AlertRuleProvider() {
			@Override public List<AlertRule> getAlertRules() {
				SpelTargetMetricsAlertRule targetMetricsAlertRule = new SpelTargetMetricsAlertRule();
				SpelTargetMetricsAlertRuleExpression targetMetricsAlertRuleExpression = new SpelTargetMetricsAlertRuleExpression();
				targetMetricsAlertRuleExpression.setRuleExpression("#{metrics.threads > 20}");
				targetMetricsAlertRuleExpression.setValueExpression("#{metrics.threads}");
				targetMetricsAlertRule.setId("test");
				targetMetricsAlertRule.setName("Thread Count");
				targetMetricsAlertRule.setRuleExpression(targetMetricsAlertRuleExpression);
				targetMetricsAlertRule.setAlertActions(Lists.newArrayList("10a836e1-a8ac-430f-890f-51490b2785a6"));
				targetMetricsAlertRule.setUndeterminedActions(Lists.newArrayList("10a836e1-a8ac-430f-890f-51490b2785a6"));
//				targetMetricsAlertRule.setOkActions(Lists.newArrayList("1"));
				return Lists.newArrayList(targetMetricsAlertRule);
			}
		};

		alertRuleProcessors = new ArrayList<>();
		alertRuleProcessors.add(new SpelTargetMetricsAlertRuleProcessor());

		alertMonitoringProcessor = new AlertMonitoringProcessor(alertRuleProvider, alertRuleProcessors, alertEventService);
	}


	@Async
	public void processMetrics(Host host) {
		Optional<Agent> agentOptional = agentService.getAgentOptional(host.getId());
		HostStatusEntity hostStatusEntity = new HostStatusEntity();
		hostStatusEntity.setId(host.getId());
		if (agentOptional.isPresent()) {
			Agent agent = agentOptional.get();

			MonitoringTargetMetrics targetMetrics = getTargetMetrics(host, agent);

			monitoring(targetMetrics);

			HostStatus hostStatus = getHostStatus(targetMetrics);
			BeanUtils.copyProperties(hostStatus, hostStatusEntity);

			hostStatusEntityService.update(hostStatusEntity);
			if (CollectionUtils.isNotEmpty(metricsExporters)) {
				metricsExporters.stream().forEach(metricsExporter -> metricsExporter.export(targetMetrics));
			}
		} else {
			monitoring(newTargetMetrics(host, null));

			hostStatusEntity.setStatus(HostStatusCode.OUT_OF_SERVICE);
			hostStatusEntity.setLastStatusTime(new Date());

			hostStatusEntityService.update(hostStatusEntity);
		}
	}

	protected void monitoring(MonitoringTargetMetrics targetMetrics) {
		try {
			alertMonitoringProcessor.monitoring(targetMetrics);
		} catch (Exception e) {
			log.error("HostAlertRuleCheck failed", e);
		}
	}

	protected MonitoringTargetMetrics getTargetMetrics(Host host, Agent agent) {
		Map<String, Object> metrics = null;
		try {
			metrics = agentClient.getMetrics(agent);

			log.debug("metrics = {}", metrics);
		} catch (Exception e) {
			log.error("Agent metrics load failed", e);
		}

		return newTargetMetrics(host, metrics);
	}

	protected MonitoringTargetMetrics newTargetMetrics(Host host, Map<String, Object> metrics) {
		Map<String, String> tags = new LinkedHashMap<>();
		tags.put("targetType", "host");
		tags.put("hostId", host.getId());
		tags.put("hostName", host.getName());
		tags.put("clusterId", host.getClusterId());
		if (host.getTags() != null) {
			tags.putAll(host.getTags());
		}

		MonitoringTargetMetrics targetMetrics = new MonitoringTargetMetrics();
		targetMetrics.setId(host.getId());
		targetMetrics.setName(host.getName());
		targetMetrics.setTenantId(host.getTenantId());
		targetMetrics.setTimestamp(System.currentTimeMillis());
		targetMetrics.setMetrics(metrics);
		targetMetrics.setTags(tags);
		return targetMetrics;
	}

	protected HostStatus getHostStatus(TargetMetrics targetMetrics) {
		HostStatus hostStatus = new HostStatus();
		hostStatus.setLastStatusTime(new Date(targetMetrics.getTimestamp()));

		Map<String, Object> metrics = targetMetrics.getMetrics();
		if (metrics != null && !metrics.isEmpty()) {
			hostStatus.setCpuUser(getDoubleValue(metrics.get("server.cpu.user")));
			hostStatus.setCpuNice(getDoubleValue(metrics.get("server.cpu.nice")));
			hostStatus.setCpuSys(getDoubleValue(metrics.get("server.cpu.sys")));

			hostStatus.setDiskTotal(getLongValue(metrics.get("server.fileSystem.total")));
			hostStatus.setDiskUsed(getLongValue(metrics.get("server.fileSystem.used")));
			hostStatus.setDiskFree(getLongValue(metrics.get("server.fileSystem.free")));

			hostStatus.setMemTotal(getLongValue(metrics.get("server.mem.total")));
			hostStatus.setMemUsed(getLongValue(metrics.get("server.mem.used")));
			hostStatus.setMemFree(getLongValue(metrics.get("server.mem.free")));

			hostStatus.setSwapTotal(getLongValue(metrics.get("server.swap.total")));
			hostStatus.setSwapUsed(getLongValue(metrics.get("server.swap.used")));
			hostStatus.setSwapFree(getLongValue(metrics.get("server.swap.free")));

			if (hostStatus.getDiskUsedPercentage() > 70
				|| hostStatus.getMemUsedPercentage() > 80) {
				hostStatus.setStatus(HostStatusCode.DOWN);
			} else {
				hostStatus.setStatus(HostStatusCode.UP);
			}

		} else {
			hostStatus.setStatus(HostStatusCode.UNKNOWN);
		}

		return hostStatus;
	}

	protected Double getDoubleValue(Object object) {
		if (object instanceof Double) {
			return (Double) object;
		} else if (object instanceof String) {
			return Double.parseDouble((String) object);
		}
		return null;

	}
	protected Long getLongValue(Object object) {
		if (object instanceof Long) {
			return (Long) object;
		} else if (object instanceof Integer) {
			return new Long((Integer)object);
		} else if (object instanceof String) {
			return Long.parseLong((String) object);
		}
		return null;
	}
}
