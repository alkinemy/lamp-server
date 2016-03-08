package lamp.collector.app.core.config;

import lamp.collector.app.core.CollectorConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = CollectorConstants.COLLECTION_HEALTH_PREFIX)
public class CollectionHealthProperties {

    private long period = 5 * 1000;

}
