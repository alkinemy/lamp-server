package lamp.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LampCollector {

	private static final String PID_FILE_NAME = "lamp-collector.pid";

	public static void main(String[] args) throws Exception {
		SpringApplication springApplication = new SpringApplication(LampCollector.class);
		springApplication.setBanner(new LampCollectorBanner());
		springApplication.addListeners(new ApplicationPidFileWriter(PID_FILE_NAME));
		springApplication.run(args);
	}

}
