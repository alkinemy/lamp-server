package lamp.common.utils;

import org.junit.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class InetAddressUtilsTest {

	@Test
	public void testGetRangeIP4Address() throws Exception {
		List<String> result1 = InetAddressUtils.getRangeIP4Address("10.1.1.[1-41]");
		assertThat(result1).hasSize(41);
		assertThat(result1.get(0)).isEqualTo("10.1.1.1");
		assertThat(result1.get(40)).isEqualTo("10.1.1.41");

		List<String> result2 = InetAddressUtils.getRangeIP4Address("host[1-3].network.com");
		assertThat(result2).hasSize(3);
		assertThat(result2.get(0)).isEqualTo("host1.network.com");
		assertThat(result2.get(2)).isEqualTo("host3.network.com");

		List<String> result3 = InetAddressUtils.getRangeIP4Address("host[07-10].network.com");
		assertThat(result3).hasSize(4);
		assertThat(result3.get(0)).isEqualTo("host07.network.com");
		assertThat(result3.get(3)).isEqualTo("host10.network.com");

		List<String> result123 = InetAddressUtils.getRangeIP4Address("10.1.1.[1-41],host[1-3].network.com\nhost[07-10].network.com");
		assertThat(result123).hasSize(48);
		assertThat(result123.get(0)).isEqualTo("10.1.1.1");
		assertThat(result123.get(47)).isEqualTo("host10.network.com");

	}
}