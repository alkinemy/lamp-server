package lamp.collector.core.config;

import lamp.collector.core.CollectorConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = CollectorConstants.COLLECTION_METRICS_PREFIX)
public class CollectionMetricsProperties {

    private long period = 5 * 1000;

}
