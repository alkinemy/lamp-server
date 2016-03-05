package lamp.collector.core.service.metrics;

import lamp.collector.core.domain.AppMetrics;
import lamp.collector.core.domain.TargetApp;
import lamp.collector.core.service.metrics.collector.AppMetricsCollectorService;
import lamp.collector.core.service.metrics.export.AppMetricsExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppMetricsCollectionService {

	@Autowired
	private AppMetricsCollectorService appMetricsCollectorService;

	@Autowired
	private AppMetricsExportService appMetricsExportService;

	public void collection(TargetApp app) {
		AppMetrics metrics = appMetricsCollectorService.getMetrics(app);
		appMetricsExportService.export(app, metrics);
	}

}
