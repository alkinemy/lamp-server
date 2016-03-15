package lamp.collector.core.service.metrics;

import lamp.common.metrics.MetricsTarget;
import lamp.metrics.exporter.MetricsExporter;
import lamp.common.metrics.TargetMetrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Slf4j
public class MetricsProcessService {

	@Autowired
	private MetricsAsyncProcessorService metricsAsyncProcessorService;

	@Autowired(required = false)
	private List<MetricsExporter> metricsExporters;

	public void process(MetricsTarget target, TargetMetrics metrics, Throwable t) {
		Optional.ofNullable(metricsExporters)
			.ifPresent(s -> s.forEach(p -> metricsAsyncProcessorService.process(p, target, metrics, t)));
	}

}
