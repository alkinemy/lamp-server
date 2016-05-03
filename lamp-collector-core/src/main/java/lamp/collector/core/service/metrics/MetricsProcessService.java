package lamp.collector.core.service.metrics;

import lamp.common.collector.model.MetricsTarget;
import lamp.common.collector.model.TargetMetrics;
import lamp.common.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

		List<TargetMetrics> metricsList = null;
		Throwable throwable = null;
		try {
			metricsList = metricsLoaderService.getMetricsList(target);
		} catch(Throwable e) {
			throwable = e;
		}

		if (CollectionUtils.isEmpty(metricsList)) {
			metricsProcessorService.process(target, null, throwable);
		} else {
			for (TargetMetrics targetMetrics : metricsList) {
				metricsProcessorService.process(target, targetMetrics, throwable);
			}
		}
	}

}
