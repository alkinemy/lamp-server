package lamp.server.aladin.admin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "lamp.server")
public class ServerProperties {

	private String localAppRepository;
	private String mavenAppRepository;

	private Long healthCheckPeriod;
	private Long metricsCheckPeriod;

	private Integer asyncThreadPoolCorePoolSize = 5;
	private Integer asyncThreadPoolMaxPoolSize = 10;


}
