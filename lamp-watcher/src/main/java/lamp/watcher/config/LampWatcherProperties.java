package lamp.watcher.config;

import lamp.common.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Getter
@Setter
@ConfigurationProperties(prefix = "lamp.watcher", ignoreUnknownFields = false)
public class LampWatcherProperties implements ApplicationListener<ApplicationEvent> {

	private int serverPort = -1;
	private int managementPort = -1;
	private String hostname;
	private String address;

	private String id;
	private String name;

	private String groupId;
	private String artifactId;
	private String version;

	private String secretKey;

	@Autowired
	private ManagementServerProperties managementServerProperties;

	@Autowired
	private ServerProperties serverProperties;

	@PostConstruct
	public void init() throws UnknownHostException {
		InetAddress inetAddress = InetAddress.getLocalHost();
		hostname = inetAddress.getHostName();
		address = inetAddress.getHostAddress();
		if (StringUtils.isBlank(version)) {
			version = LampWatcherProperties.class.getPackage().getImplementationVersion();
		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof EmbeddedServletContainerInitializedEvent) {
			EmbeddedServletContainerInitializedEvent initEvent = (EmbeddedServletContainerInitializedEvent) event;
			if ("management".equals(initEvent.getApplicationContext().getNamespace())) {
				managementPort = initEvent.getEmbeddedServletContainer().getPort();
			} else {
				serverPort = initEvent.getEmbeddedServletContainer().getPort();
			}
		}
	}

	public String getId() {
		if (StringUtils.isBlank(id)) {
			return getHostname() + "-" + getPort();
		} else {
			return id;
		}
	}

	public int getPort() {
		return managementPort != -1 ? managementPort : serverPort;
	}

}
