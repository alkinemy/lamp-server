package lamp.metrics.exporter.slf4j;

import lamp.metrics.exporter.HealthExporter;
import lamp.common.collector.model.TargetHealth;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class Slf4jHealthExporter extends HealthExporter {

	private Logger logger;

	public Slf4jHealthExporter(String loggerName) {
		this.logger = LoggerFactory.getLogger(loggerName);
	}

	@Override
	public void export(TargetHealth targetHealth) {
		logger.info("health = {}", targetHealth);
	}

}