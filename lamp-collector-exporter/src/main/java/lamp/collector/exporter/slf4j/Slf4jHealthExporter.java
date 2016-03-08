package lamp.collector.exporter.slf4j;

import lamp.common.collection.health.HealthExporter;
import lamp.common.collection.health.TargetHealth;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class Slf4jHealthExporter implements HealthExporter {

	private Logger logger;

	public Slf4jHealthExporter(String loggerName) {
		this.logger = LoggerFactory.getLogger(loggerName);
	}

	@Override
	public void export(TargetHealth targetHealth) {
		logger.info("targetHealth = {}", targetHealth);
	}

}