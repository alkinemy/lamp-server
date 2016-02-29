package lamp.watcher.core.config.export;

import lamp.watcher.core.config.base.KafkaProducerProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "export.kafka.metrics")
public class ExportMetricsKafkaProperties extends KafkaProducerProperties {

}
