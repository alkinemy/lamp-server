package lamp.watcher.core.config.monitoring;

import lamp.watcher.core.config.base.KafkaConsumerProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "monitoring.health.kafka", ignoreUnknownFields = true)
public class MonitoringHealthKafkaProperties extends KafkaConsumerProperties {


}
