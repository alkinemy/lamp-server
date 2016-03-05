package lamp.collector.core.service.metrics;

import lamp.admin.utils.BooleanUtils;
import lamp.collector.core.domain.AppMetrics;
import lamp.collector.core.domain.WatchedApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppMetricsMonitoringService {

	public void monitoring(WatchedApp watchedApp, AppMetrics appMetrics) {
		if (BooleanUtils.isTrue(watchedApp.getMetricsMonitoringEnabled())) {

		}
		log.info("monitoring : app = {}, metrics = {}", watchedApp, appMetrics);
	}

}
