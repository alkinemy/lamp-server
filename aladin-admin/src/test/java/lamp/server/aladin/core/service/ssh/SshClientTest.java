package lamp.server.aladin.core.service.ssh;

import lamp.server.aladin.core.support.ssh.SshClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SshClientTest {

	private SshClient sshClient;
	@Before
	public void setUp() throws Exception {
		sshClient = new SshClient("localhost", 22);
		sshClient.connect("kangwoo", "geena11@");
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testExec() throws Exception {
		sshClient.exec("ls -al");
	}
}