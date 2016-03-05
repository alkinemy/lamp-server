package lamp.collector.core.service.metrics.export;

import lamp.collector.core.domain.*;
import lamp.collector.core.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MetricsExportFacadeService {

	@Autowired
	private EventService eventService;

	@Autowired(required = false)
	private List<MetricsExportService> metricsExportServices;

	public void export(CollectionTarget collectionTarget, TargetMetrics metrics) {
		if (metricsExportServices != null) {
			for (MetricsExportService metricsExportService : metricsExportServices) {
				try {
					metricsExportService.export(collectionTarget, metrics);
				} catch(Throwable e) {
					log.warn("Metrics Export failed", e);
					Event event = Event.of(collectionTarget, EventName.METRICS_EXPORT_FAILED, AppEventLevel.WARN, e);
					eventService.publish(event);
				}
			}
		}
	}

}
