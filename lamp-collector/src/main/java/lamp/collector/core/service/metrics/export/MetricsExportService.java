package lamp.collector.core.service.metrics.export;

import lamp.collector.core.domain.TargetMetrics;
import lamp.collector.core.domain.CollectionTarget;

public interface MetricsExportService {

	void export(CollectionTarget collectionTarget, TargetMetrics metrics);

}