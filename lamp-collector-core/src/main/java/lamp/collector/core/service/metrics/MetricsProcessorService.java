package lamp.collector.core.service.metrics;

import lamp.common.collector.model.MetricsTarget;
import lamp.collector.metrics.exporter.MetricsExporter;
import lamp.common.collector.model.TargetMetrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Slf4j
public class MetricsProcessorService {

	@Autowired
	private MetricsProcessorAsyncSupportService metricsProcessorAsyncSupportService;

	@Autowired(required = false)
	private List<MetricsExporter> metricsExporters;

	public void process(MetricsTarget target, TargetMetrics metrics, Throwable t) {
		Optional.ofNullable(metricsExporters)
			.ifPresent(s -> s.stream()
					.filter(p -> p.canProcess(target))
					.forEach(p -> metricsProcessorAsyncSupportService.process(p, target, metrics, t)));
	}

}
