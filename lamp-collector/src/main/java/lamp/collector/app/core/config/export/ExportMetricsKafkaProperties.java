package lamp.collector.app.core.config.export;

import lamp.collector.app.core.CollectorConstants;
import lamp.collector.exporter.kafka.KafkaProducerProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = CollectorConstants.EXPORT_METRICS_KAFKA_PREFIX)
public class ExportMetricsKafkaProperties extends KafkaProducerProperties {

}
