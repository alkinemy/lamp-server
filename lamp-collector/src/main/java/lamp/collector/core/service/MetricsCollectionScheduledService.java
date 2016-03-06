package lamp.collector.core.service;

import lamp.admin.utils.BooleanUtils;
import lamp.collector.core.domain.CollectionTarget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Slf4j
public class MetricsCollectionScheduledService {

	@Autowired
	private CollectionTargetDelegateService collectionTargetDelegateService;

	@Autowired
	private MetricsCollectionFacadeService metricsCollectionFacadeService;

	public void collection() {
		Collection<CollectionTarget> collectionTargets = collectionTargetDelegateService.getCollectionTargetListForMetrics();
		collectionTargets.stream()
				.filter(a -> BooleanUtils.isTrue(a.getHealthCollectionEnabled()))
				.forEach(this::metricsMonitoring);
	}

	protected void metricsMonitoring(CollectionTarget collectionTarget) {
		try {
			metricsCollectionFacadeService.collection(collectionTarget);
		} catch (Exception e) {
			log.warn("Metrics Collection Failed", e);
		}
	}

}
