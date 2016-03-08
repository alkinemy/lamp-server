package lamp.collector.app.core.service.metrics;

import lamp.collector.app.core.domain.EventName;
import lamp.collector.app.core.domain.TargetMetricsType;
import lamp.collector.common.CollectionTarget;
import lamp.collector.common.TargetMetrics;
import lamp.collector.common.metrics.MetricsLoader;
import lamp.collector.loader.rest.RestTemplateMetricsLoader;
import lamp.event.common.Event;
import lamp.event.common.EventLevel;
import lamp.event.common.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
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
