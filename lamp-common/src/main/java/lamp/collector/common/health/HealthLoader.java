package lamp.collector.common.health;

import lamp.collector.common.CollectionTarget;
import lamp.collector.common.TargetHealth;

public interface HealthLoader {

	TargetHealth getHealth(CollectionTarget collectionTarget);

}
