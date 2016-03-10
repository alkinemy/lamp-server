package lamp.collector.core.service;

import lamp.common.collection.CollectionTarget;

import java.util.List;

public interface CollectionTargetService {

	List<CollectionTarget> getCollectionTargetListForHealth();

	List<CollectionTarget> getCollectionTargetListForMetrics();

}
