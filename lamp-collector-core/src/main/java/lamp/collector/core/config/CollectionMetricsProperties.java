package lamp.collector.core.config;

import lamp.collector.core.CollectorConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = CollectorConstants.COLLECTION_METRICS_PREFIX, ignoreUnknownFields = true)
public class CollectionMetricsProperties {

    private long interval = 5 * 1000;

    private CollectionMetricsKafkaProperties kafka;

}
