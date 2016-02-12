package lamp.server.aladin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LampServer {

	private static final String PID_FILE_NAME = "lamp-admin.pid";

	public static void main(String[] args) throws Exception {
		SpringApplication springApplication = new SpringApplication(LampServer.class);
		springApplication.setBanner(new LampServerBanner());
		springApplication.addListeners(new ApplicationPidFileWriter(PID_FILE_NAME));
		springApplication.run(args);
	}

}
