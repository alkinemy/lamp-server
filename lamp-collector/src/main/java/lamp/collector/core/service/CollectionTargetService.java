package lamp.collector.core.service;


import lamp.collector.core.domain.CollectionTarget;

import java.util.List;

public interface CollectionTargetService {


	List<CollectionTarget> getCollectionTargetListForHealth();

	List<CollectionTarget> getCollectionTargetListForMetrics();

}
