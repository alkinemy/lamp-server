package lamp.watcher.core.support.jmx;

import org.junit.Test;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.ObjectName;

import java.util.Set;

import static org.junit.Assert.*;


public class JmxConnectionFactoryTest {

	@Test
	public void testGetJmxConnection() throws Exception {
		JmxConnectionProperties properties = new JmxConnectionProperties();
		properties.setHost("localhost");
		properties.setPort(1099);

		JmxConnectionFactory connectionFactory = new JmxConnectionFactory();
		JmxConnection jmxConnection = connectionFactory.getJmxConnection(properties);

		// java.lang:type=MemoryPool
		// java.lang:type=Memory

		Set<ObjectName> objectNames = jmxConnection.getMBeanServerConnection().queryNames(null, null);
		for (ObjectName name : objectNames) {
			MBeanInfo info = jmxConnection.getMBeanServerConnection().getMBeanInfo(name);
			System.out.println("name = " + name);
			System.out.println("info = " + info);
//			java.lang.management.MemoryMXBean
//			MBeanAttributeInfo[] attributes = getAttributesInfo(name);
//			MBeanOperationInfo[] operations = getOperationsInfo(name);
		}
	}

}