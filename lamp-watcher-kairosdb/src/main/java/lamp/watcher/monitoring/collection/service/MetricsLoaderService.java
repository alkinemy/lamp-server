package lamp.watcher.monitoring.collection.service;

import lamp.common.event.Event;
import lamp.common.event.EventLevel;
import lamp.common.event.EventPublisher;
import lamp.common.collector.MetricsLoader;
import lamp.common.collector.MetricsTarget;
import lamp.common.collector.TargetMetrics;
import lamp.watcher.core.domain.EventName;
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
		loaderMap = new HashMap<>();
//		loaderMap.put(TargetMetricsType.SPRING_BOOT, new RestTemplateMetricsLoader());
	}

	public TargetMetrics getMetrics(MetricsTarget metricsTarget) {
		try {
			MetricsLoader loader = loaderMap.get(metricsTarget.getMetricsType());
			return loader.getMetrics(metricsTarget);
		} catch(Throwable e) {
			log.warn("Metrics Load failed", e);

			Event event = new Event(EventLevel.WARN, EventName.METRICS_LOAD_FAILED, e);
			eventPublisher.publish(event);
		}
		return null;
	}

}
