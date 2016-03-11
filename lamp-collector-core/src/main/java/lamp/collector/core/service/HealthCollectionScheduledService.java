package lamp.collector.core.service;

import lamp.collector.core.service.health.HealthCollectionService;
import lamp.common.metrics.HealthTarget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Slf4j
public class HealthCollectionScheduledService {

	@Autowired
	private HealthTargetService healthTargetService;

	@Autowired
	private HealthCollectionService healthCollectionService;

	public void collection() {
		Collection<HealthTarget> collectionTargets = healthTargetService.getHealthTargets();
		collectionTargets.stream()
				.forEach(this::collection);
	}

	protected void collection(HealthTarget healthTarget) {
		try {
			healthCollectionService.collection(healthTarget);
		} catch (Exception e) {
			log.warn("Health Collection Failed", e);
		}
	}

}
