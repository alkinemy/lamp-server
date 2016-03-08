package lamp.watcher.core.config.monitoring;

import lamp.support.kafka.KafkaConsumerProperties;
import lamp.watcher.core.config.ConfigConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = ConfigConstants.MONITORING_METRICS_KAFKA_PREFIX, ignoreUnknownFields = true)
public class MonitoringMetricsKafkaProperties extends KafkaConsumerProperties {


}
