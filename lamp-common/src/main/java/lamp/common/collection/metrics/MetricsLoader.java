package lamp.common.collection.metrics;

import lamp.common.collection.CollectionTarget;

public interface MetricsLoader {

	TargetMetrics getMetrics(CollectionTarget collectionTarget);

}
