package lamp.collector.app.core.config.export;

import lamp.collector.app.core.CollectorConstants;
import lamp.support.kafka.KafkaProducerProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = CollectorConstants.EXPORT_HEALTH_KAFKA_PREFIX)
public class ExportHealthKafkaProperties extends KafkaProducerProperties {

}
