package lamp.collector.core.service.metrics.collector;

import lamp.collector.core.domain.TargetMetrics;
import lamp.collector.core.domain.CollectionTarget;

public interface MetricsCollector {

	TargetMetrics getMetrics(CollectionTarget collectionTarget);

}
