package lamp.collector.common.metrics;

import lamp.collector.common.CollectionTarget;
import lamp.collector.common.TargetMetrics;

public interface MetricsLoader {

	TargetMetrics getMetrics(CollectionTarget collectionTarget);

}
