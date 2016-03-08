package lamp.collector.common.service.metrics;

import lamp.common.utils.BooleanUtils;
import lamp.collector.common.domain.AppMetrics;
import lamp.collector.common.domain.MonitoringTarget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppMetricsMonitoringService {

	public void monitoring(MonitoringTarget watchedApp, AppMetrics appMetrics) {
		if (BooleanUtils.isTrue(watchedApp.getMetricsMonitoringEnabled())) {

		}
		log.info("monitoring : app = {}, metrics = {}", watchedApp, appMetrics);
	}

}
