package lamp.collector.core.service.health;

import lamp.collector.core.domain.EventName;
import lamp.common.event.Event;
import lamp.common.event.EventLevel;
import lamp.common.event.EventPublisher;
import lamp.common.metrics.HealthExporter;
import lamp.common.metrics.TargetHealth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class HealthExportService {

	@Autowired
	private EventPublisher eventPublisher;

	@Autowired(required = false)
	private List<HealthExporter> healthExporters;

	public void export(TargetHealth health) {
		if (healthExporters != null) {
			for (HealthExporter healthExporter : healthExporters) {
				try {
					healthExporter.export(health);
				} catch(Throwable e) {
					log.warn("Health Export failed", e);

					Event event = new Event(EventLevel.WARN, EventName.HEALTH_EXPORT_FAILED, e);
					eventPublisher.publish(event);
				}
			}
		}
	}

}
