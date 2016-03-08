package lamp.collector.app.core.service.health;

import lamp.collector.app.core.domain.EventName;
import lamp.collector.common.TargetHealth;
import lamp.collector.common.health.HealthExporter;
import lamp.event.common.Event;
import lamp.event.common.EventLevel;
import lamp.event.common.EventPublisher;
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
