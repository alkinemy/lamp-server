package lamp.collector.core.health.handler.exporter.slf4j;

import lamp.collector.core.health.TargetHealth;
import lamp.collector.core.health.handler.exporter.TargetHealthExporter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class Slf4JTargetHealthExporter implements TargetHealthExporter {

	private Logger logger;

	public Slf4JTargetHealthExporter(String loggerName) {
		this.logger = LoggerFactory.getLogger(loggerName);
	}

	@Override
	public void handle(TargetHealth targetHealth) {
		logger.info("targetHealth = {}", targetHealth);
	}

}