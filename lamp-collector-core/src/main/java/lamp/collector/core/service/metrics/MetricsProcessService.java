package lamp.collector.core.service.metrics;

import lamp.common.collector.model.MetricsTarget;
import lamp.common.collector.model.TargetMetrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class MetricsProcessService {

	@Autowired
	private MetricsLoaderService metricsLoaderService;

	@Autowired
	private MetricsProcessorService metricsProcessorService;

	public void process(MetricsTarget target) {
		if (!target.isMetricsCollectionEnabled()) {
			return;
		}

		TargetMetrics metrics = null;
		Throwable throwable = null;
		try {
			metrics = metricsLoaderService.getMetrics(target);
		} catch(Throwable e) {
			throwable = e;
		}

		metricsProcessorService.process(target ,metrics, throwable);
	}

}
