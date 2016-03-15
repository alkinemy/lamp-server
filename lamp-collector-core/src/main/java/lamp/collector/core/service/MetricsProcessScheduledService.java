package lamp.collector.core.service;

import lamp.collector.core.service.metrics.MetricsProcessFacadeService;
import lamp.common.metrics.MetricsTarget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Slf4j
public class MetricsProcessScheduledService {

	@Autowired
	private MetricsTargetService metricsTargetService;

	@Autowired
	private MetricsProcessFacadeService metricsProcessFacadeService;

	public void process() {
		Collection<MetricsTarget> collectionTargets = metricsTargetService.getMetricsTargets();
		collectionTargets.stream()
				.forEach(metricsProcessFacadeService::process);
	}

}
