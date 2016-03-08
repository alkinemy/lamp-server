package lamp.collector.app.core.service.metrics;

import lamp.common.collection.CollectionTarget;
import lamp.common.collection.metrics.TargetMetrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MetricsCollectionService {

	@Autowired
	private MetricsLoaderService metricsCollectorService;

	@Autowired
	private MetricsExporterService metricsExporterService;

	public void collection(CollectionTarget collectionTarget) {
		TargetMetrics metrics = metricsCollectorService.getMetrics(collectionTarget);
		if (metrics != null) {
			metricsExporterService.export(metrics);
		}
	}

}
