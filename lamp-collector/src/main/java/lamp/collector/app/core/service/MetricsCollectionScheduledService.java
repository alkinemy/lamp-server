package lamp.collector.app.core.service;

import lamp.common.utils.BooleanUtils;
import lamp.collector.app.core.service.metrics.MetricsCollectionService;
import lamp.collector.common.CollectionTarget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Slf4j
public class MetricsCollectionScheduledService {

	@Autowired
	private CollectionTargetService collectionTargetService;

	@Autowired
	private MetricsCollectionService metricsCollectionService;

	public void collection() {
		Collection<CollectionTarget> collectionTargets = collectionTargetService.getCollectionTargetListForMetrics();
		collectionTargets.stream()
				.filter(a -> BooleanUtils.isTrue(a.getHealthCollectionEnabled()))
				.forEach(this::collection);
	}

	protected void collection(CollectionTarget collectionTarget) {
		try {
			metricsCollectionService.collection(collectionTarget);
		} catch (Exception e) {
			log.warn("Metrics Collection Failed", e);
		}
	}

}
