package lamp.watcher.monitoring.collection.service;

import lamp.metrics.loader.rest.RestTemplateMetricsLoader;
import lamp.common.collection.CollectionTarget;
import lamp.common.event.Event;
import lamp.common.event.EventLevel;
import lamp.common.event.EventPublisher;
import lamp.common.metrics.MetricsLoader;
import lamp.common.metrics.TargetMetrics;
import lamp.watcher.core.domain.EventName;
import lamp.watcher.core.domain.TargetMetricsType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MetricsLoaderService {

	@Autowired
	private EventPublisher eventPublisher;

	private Map<String, MetricsLoader> loaderMap;

	public MetricsLoaderService() {
		// TODO 수정해라
		loaderMap = new HashMap<>();
		loaderMap.put(TargetMetricsType.SPRING_BOOT, new RestTemplateMetricsLoader());
	}

	public TargetMetrics getMetrics(CollectionTarget collectionTarget) {
		try {
			MetricsLoader loader = loaderMap.get(collectionTarget.getMetricsType());
			return loader.getMetrics(collectionTarget);
		} catch(Throwable e) {
			log.warn("Metrics Load failed", e);

			Event event = new Event(EventLevel.WARN, EventName.METRICS_LOAD_FAILED, e);
			eventPublisher.publish(event);
		}
		return null;
	}

}
