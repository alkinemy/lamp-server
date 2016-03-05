package lamp.collector.core.service.health.export;

import lamp.collector.core.domain.*;
import lamp.collector.core.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class HealthExportFacadeService {

	@Autowired
	private EventService eventService;

	@Autowired(required = false)
	private List<HealthExportService> healthExportServices;

	public void export(CollectionTarget collectionTarget, TargetHealth health) {
		if (healthExportServices != null) {
			for (HealthExportService healthExportService : healthExportServices) {
				try {
					healthExportService.export(collectionTarget, health);
				} catch(Throwable e) {
					log.warn("Health Export failed", e);

					Event event = Event.of(collectionTarget, EventName.HEALTH_EXPORT_FAILED, AppEventLevel.WARN, e);
					eventService.publish(event);
				}
			}
		}
	}

}
