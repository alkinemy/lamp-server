package lamp.collector.common.config.monitoring;

import lamp.collector.common.config.base.KafkaConsumerProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "monitoring.health.kafka", ignoreUnknownFields = true)
public class MonitoringHealthKafkaProperties extends KafkaConsumerProperties {


}
