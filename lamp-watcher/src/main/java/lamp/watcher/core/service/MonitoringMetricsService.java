package lamp.watcher.core.service;

import lamp.common.collection.metrics.TargetMetrics;
import lamp.common.monitoring.MonitoringTarget;
import lamp.common.utils.BooleanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MonitoringMetricsService {

	public void monitoring(MonitoringTarget monitoringTarget, TargetMetrics targetMetrics) {
		if (BooleanUtils.isTrue(monitoringTarget.getMetricsMonitoringEnabled())) {

		}
	}

}
