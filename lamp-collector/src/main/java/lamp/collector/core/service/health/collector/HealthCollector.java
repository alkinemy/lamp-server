package lamp.collector.core.service.health.collector;

import lamp.collector.core.domain.TargetHealth;
import lamp.collector.core.domain.CollectionTarget;

public interface HealthCollector {

	TargetHealth getHealth(CollectionTarget collectionTarget);

}
