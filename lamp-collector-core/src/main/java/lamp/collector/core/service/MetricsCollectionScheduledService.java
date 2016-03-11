package lamp.collector.core.service;

import lamp.collector.core.service.metrics.MetricsCollectionService;
import lamp.common.metrics.MetricsTarget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Slf4j
public class MetricsCollectionScheduledService {

	@Autowired
	private MetricsTargetService metricsTargetService;

	@Autowired
	private MetricsCollectionService metricsCollectionService;

	public void collection() {
		Collection<MetricsTarget> collectionTargets = metricsTargetService.getMetricsTargets();
		collectionTargets.stream()
				.forEach(this::collection);
	}

	protected void collection(MetricsTarget metricsTarget) {
		try {
			metricsCollectionService.collection(metricsTarget);
		} catch (Exception e) {
			log.warn("Metrics Collection Failed", e);
		}
	}

}
