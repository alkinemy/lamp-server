package lamp.common.collection.health;

import lamp.common.collection.CollectionTarget;

public interface HealthLoader {

	TargetHealth getHealth(CollectionTarget collectionTarget);

}
