package lamp.collector.core.service.metrics;

import lamp.collector.core.domain.TargetMetrics;
import lamp.collector.core.domain.CollectionTarget;
import lamp.collector.core.service.metrics.collector.MetricsCollectorService;
import lamp.collector.core.service.metrics.export.MetricsExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MetricsCollectionService {

	@Autowired
	private MetricsCollectorService metricsCollectorService;

	@Autowired
	private MetricsExportService metricsExportService;

	public void collection(CollectionTarget collectionTarget) {
		TargetMetrics metrics = metricsCollectorService.getMetrics(collectionTarget);
		metricsExportService.export(collectionTarget, metrics);
	}

}
