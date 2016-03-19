package lamp.collector.core.service;

import lamp.collector.core.service.metrics.MetricsProcessService;
import lamp.common.collector.model.MetricsTarget;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Slf4j
public class MetricsProcessScheduledService {

	@Autowired
	private MetricsTargetService metricsTargetService;

	@Autowired
	private MetricsProcessService metricsProcessService;

	public void process() {
		Collection<MetricsTarget> collectionTargets = metricsTargetService.getMetricsTargets();
		collectionTargets.stream()
				.forEach(metricsProcessService::process);
	}

}
