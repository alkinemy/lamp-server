package lamp.collector.app.core.service;

import lamp.collector.common.CollectionTarget;

import java.util.List;

public interface CollectionTargetService {


	List<CollectionTarget> getCollectionTargetListForHealth();

	List<CollectionTarget> getCollectionTargetListForMetrics();

}
