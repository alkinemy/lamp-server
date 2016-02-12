package lamp.server.aladin.admin.config.metrics;

import lamp.server.aladin.admin.config.ServerProperties;
import lamp.server.aladin.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "metrics.export.kafka")
public class KafkaProperties {

	private String topic;

	private String bootstrapServers;
	private String clientId;


	@Autowired
	private ServerProperties serverProperties;

	public String getClientId() {
		if (StringUtils.isBlank(clientId)) {
			return serverProperties.getHostname() + "-" + serverProperties.getPort();
		} else {
			return clientId;
		}
	}
}
