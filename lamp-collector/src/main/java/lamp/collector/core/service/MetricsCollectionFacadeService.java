package lamp.collector.core.service;

import lamp.collector.core.domain.Event;
import lamp.collector.core.domain.AppEventLevel;
import lamp.collector.core.domain.EventName;
import lamp.collector.core.domain.CollectionTarget;
import lamp.collector.core.service.metrics.MetricsCollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MetricsCollectionFacadeService {

	@Autowired
	private MetricsCollectionService metricsCollectionService;

	@Autowired
	private EventService eventService;

	public void collection(CollectionTarget collectionTarget) {
		try {
			metricsCollectionService.collection(collectionTarget);
		} catch (Throwable e) {
			log.warn("Metrics Collection Failed ", e);
			Event event = Event.of(collectionTarget, EventName.METRICS_COLLECTION_FAILED, AppEventLevel.WARN, e);
			eventService.publish(event);
		}
	}

}
