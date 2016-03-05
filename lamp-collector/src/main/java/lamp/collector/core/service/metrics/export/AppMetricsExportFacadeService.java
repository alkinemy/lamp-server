package lamp.collector.core.service.metrics.export;

import lamp.collector.core.domain.*;
import lamp.collector.core.service.AppEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AppMetricsExportFacadeService {

	@Autowired
	private AppEventService appEventService;

	@Autowired(required = false)
	private List<AppMetricsExportService> appMetricsExportServices;

	public void export(TargetApp app, AppMetrics metrics) {
		if (appMetricsExportServices != null) {
			for (AppMetricsExportService appMetricsExportService : appMetricsExportServices) {
				try {
					appMetricsExportService.export(app, metrics);
				} catch(Throwable e) {
					log.warn("Metrics Export failed", e);
					AppEvent appEvent = AppEvent.of(app, AppEventName.METRICS_EXPORT_FAILED, AppEventLevel.WARN, e);
					appEventService.publish(appEvent);
				}
			}
		}
	}

}
