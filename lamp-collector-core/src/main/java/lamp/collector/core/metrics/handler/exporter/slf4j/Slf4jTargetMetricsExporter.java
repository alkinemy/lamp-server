package lamp.collector.core.metrics.handler.exporter.slf4j;

import lamp.collector.core.metrics.TargetMetrics;
import lamp.collector.core.metrics.handler.exporter.TargetMetricsExporter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class Slf4jTargetMetricsExporter implements TargetMetricsExporter {

	private Logger logger;

	public Slf4jTargetMetricsExporter(String loggerName) {
		this.logger = LoggerFactory.getLogger(loggerName);
	}

	@Override
	public void handle(TargetMetrics targetMetrics) {
		logger.info("targetMetrics = {}", targetMetrics);
	}

}