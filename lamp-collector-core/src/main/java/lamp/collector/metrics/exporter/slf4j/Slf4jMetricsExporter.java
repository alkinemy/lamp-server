package lamp.collector.metrics.exporter.slf4j;

import lamp.collector.metrics.exporter.MetricsExporter;
import lamp.common.collector.model.TargetMetrics;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class Slf4jMetricsExporter extends MetricsExporter {

	private Logger logger;

	public Slf4jMetricsExporter(String loggerName) {
		this.logger = LoggerFactory.getLogger(loggerName);
	}

	@Override
	public void export(TargetMetrics targetMetrics) {
		logger.info("metrics = {}", targetMetrics);
	}

}