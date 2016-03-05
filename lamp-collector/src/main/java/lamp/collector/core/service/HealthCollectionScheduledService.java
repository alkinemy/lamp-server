package lamp.collector.core.service;

import lamp.admin.utils.BooleanUtils;
import lamp.collector.core.domain.CollectionTarget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Slf4j
public class HealthCollectionScheduledService {

	@Autowired
	private CollectionTargetDelegateService collectionTargetDelegateService;

	@Autowired
	private HealthCollectionFacadeService healthCollectionFacadeService;

	public void collection() {
		Collection<CollectionTarget> collectionTargets = collectionTargetDelegateService.getCollectionTargetListForHealth();
		collectionTargets.stream()
				.filter(a -> BooleanUtils.isTrue(a.getHealthCollectionEnabled()))
				.forEach(this::collection);
	}

	protected void collection(CollectionTarget app) {
		try {
			healthCollectionFacadeService.collection(app);
		} catch (Exception e) {
			log.warn("Health Collection Failed", e);
		}
	}

}
