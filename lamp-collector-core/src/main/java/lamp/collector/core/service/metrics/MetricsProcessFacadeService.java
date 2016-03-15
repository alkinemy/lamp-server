package lamp.collector.core.service.metrics;

import lamp.common.metrics.MetricsTarget;
import lamp.common.metrics.TargetMetrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

@Slf4j
public class MetricsProcessFacadeService {

	@Autowired
	private MetricsLoadService metricsLoadService;

	@Autowired
	private MetricsProcessService metricsProcessService;

	@Async
	public void process(MetricsTarget target) {
		TargetMetrics metrics = null;
		Throwable throwable = null;
		try {
			metrics = metricsLoadService.getMetrics(target);
		} catch(Throwable e) {
			throwable = e;
		}

		metricsProcessService.process(target ,metrics, throwable);
	}

}
