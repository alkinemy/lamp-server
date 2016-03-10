package lamp.collector.core.config;

import lamp.collector.core.CollectorConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = CollectorConstants.COLLECTION_HEALTH_PREFIX)
public class CollectionHealthProperties {

    private long interval = 5 * 1000;

}
