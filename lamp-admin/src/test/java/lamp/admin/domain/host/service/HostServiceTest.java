package lamp.admin.domain.host.service;

import lamp.admin.domain.host.model.ScannedHost;
import lamp.common.utils.InetAddressUtils;
import lamp.common.utils.InetAddressUtilsTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class HostServiceTest {


	@Test
	public void testScanHost_Form() throws Exception {

	}

	@Test
	public void testScanHost() throws Exception {
		HostService hostService = new HostService();
		ScannedHost scannedHost = hostService.scanHost("127.0.0.1", 22);
	}

	@Test
	public void testAddHost() throws Exception {
		System.out.println(InetAddressUtils.getCanonicalHostName("127.0.0.1", "10.210.71.19"));
	}

}