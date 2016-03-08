package lamp.watcher.support.jpa.service;

import lamp.common.collection.health.TargetHealth;
import lamp.common.collection.metrics.TargetMetrics;
import lamp.common.monitoring.MonitoringTarget;
import lamp.watcher.core.service.MonitoringTargetService;
import lamp.watcher.support.jpa.domain.JpaMonitoringTarget;
import lamp.watcher.support.jpa.repository.JpaMonitoringTargetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class JpaMonitoringTargetService implements MonitoringTargetService {

	@Autowired
	private JpaMonitoringTargetRepository jpaMonitoringTargetRepository;

	public List<MonitoringTarget> getWatchedAppList() {
		return jpaMonitoringTargetRepository.findAll().stream().collect(Collectors.toList());
	}

	public List<MonitoringTarget> getMonitoringTargetsForHealthCollection() {
		return getWatchedAppList();
	}

	public List<MonitoringTarget> getMonitoringTargetsForMetricsCollection() {
		return getWatchedAppList();
	}

	public Optional<MonitoringTarget> getMonitoringTargetOptional(String id) {
		return Optional.ofNullable(jpaMonitoringTargetRepository.findOne(id));
	}

	@Override public MonitoringTarget createMonitoringTarget(TargetHealth targetHealth) {
		JpaMonitoringTarget monitoringTarget = new JpaMonitoringTarget();
		// TODO 구현

		monitoringTarget.setId(targetHealth.getId());
		monitoringTarget.setName(targetHealth.getName());

		Map<String, String> tags = targetHealth.getTags();
		if (tags != null) {
			monitoringTarget.setHostname(tags.get("hostname"));
			monitoringTarget.setGroupId(tags.get("groupId"));
			monitoringTarget.setArtifactId(tags.get("artifactId"));
			monitoringTarget.setVersion(tags.get("version"));
		}

		return monitoringTarget;
	}

	@Override public MonitoringTarget createMonitoringTarget(TargetMetrics targetMetrics) {
		JpaMonitoringTarget monitoringTarget = new JpaMonitoringTarget();
		// TODO 구현

		monitoringTarget.setId(targetMetrics.getId());
		monitoringTarget.setName(targetMetrics.getName());

		Map<String, String> tags = targetMetrics.getTags();
		if (tags != null) {
			monitoringTarget.setHostname(tags.get("hostname"));
			monitoringTarget.setGroupId(tags.get("groupId"));
			monitoringTarget.setArtifactId(tags.get("artifactId"));
			monitoringTarget.setVersion(tags.get("version"));
		}

		return monitoringTarget;
	}

}
