package lamp.admin.domain.host.service;

import lamp.admin.domain.host.model.ScannedHost;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class HostScanServiceTest {

	@Test
	public void testScanHost() throws Exception {


	}

	@Test
	public void testScanHostConnected() throws Exception {
		HostScanService hostScanService = new HostScanService();
		String host = "127.0.0.1";
		int port = 22;
		boolean connected = hostScanService.scanHostConnected(host, port);
		assertThat(connected).isTrue();
	}

	@Test
	public void testScanHostConnected_False() throws Exception {
		HostScanService hostScanService = new HostScanService();
		String host = "127.0.0.1";
		int port = 23;
		boolean connected = hostScanService.scanHostConnected(host, port);
		assertThat(connected).isFalse();
	}

	@Test
	public void testScanHostManaged() throws Exception {

	}
}