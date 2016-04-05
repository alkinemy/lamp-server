package lamp.admin.web.config;

import lamp.common.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Getter
@Setter
@ConfigurationProperties(prefix = "lamp.server")
public class ServerProperties implements ApplicationListener<ApplicationEvent> {

	private String localAppRepository;
	private String mavenAppRepository;

	private int connectTimeout = 3 * 1000;
	private int connectionRequestTimeout = 3 * 1000;
	private int readTimeout = 10 * 1000;

	private int serverPort = -1;
	private int managementPort = -1;
	private String hostname;
	private String address;
	private String version;

	@PostConstruct
	public void init() throws UnknownHostException {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			hostname = inetAddress.getHostName();
			address = inetAddress.getHostAddress();
		} catch (UnknownHostException e) {
			hostname = e.getMessage();
		}

		if (StringUtils.isBlank(version)) {
			version = ServerProperties.class.getPackage().getImplementationVersion();
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

	public int getPort() {
		return managementPort != -1 ? managementPort : serverPort;
	}
}
