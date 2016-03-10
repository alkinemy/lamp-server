package lamp.collector.core.service;

import lamp.collector.core.service.health.HealthCollectionService;
import lamp.common.collection.CollectionTarget;
import lamp.common.utils.BooleanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Slf4j
public class HealthCollectionScheduledService {

	@Autowired
	private CollectionTargetService collectionTargetService;

	@Autowired
	private HealthCollectionService healthCollectionService;

	public void collection() {
		Collection<CollectionTarget> collectionTargets = collectionTargetService.getCollectionTargetListForHealth();
		collectionTargets.stream()
				.filter(a -> BooleanUtils.isTrue(a.getHealthCollectionEnabled()))
				.forEach(this::collection);
	}

	protected void collection(CollectionTarget collectionTarget) {
		try {
			healthCollectionService.collection(collectionTarget);
		} catch (Exception e) {
			log.warn("Health Collection Failed", e);
		}
	}

}
