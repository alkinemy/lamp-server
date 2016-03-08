package lamp.collector.loader.jmx.jmx;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.Map;

public class JmxConnectionFactory {

	public JmxConnection getJmxConnection(JmxConnectionProperties properties) throws IOException {
		JMXServiceURL serviceURL = new JMXServiceURL(properties.getUrl());
		JMXConnector connector = getServerConnector(serviceURL, properties.getEnvironment());
		return new JmxConnection(connector, connector.getMBeanServerConnection());
	}

	protected JMXConnector getServerConnector(JMXServiceURL serviceURL, Map<String, Object> environment) throws IOException {
		return JMXConnectorFactory.connect(serviceURL, environment);
	}



}
