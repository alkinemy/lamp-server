package lamp.collector.core.config.monitoring;

import lamp.collector.core.config.base.KafkaConsumerProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "monitoring.metrics.kafka", ignoreUnknownFields = true)
public class MonitoringMetricsKafkaProperties extends KafkaConsumerProperties {


}
