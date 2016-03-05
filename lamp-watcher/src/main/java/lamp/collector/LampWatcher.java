package lamp.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LampWatcher {

	private static final String PID_FILE_NAME = "lamp-watcher.pid";

	public static void main(String[] args) throws Exception {
		SpringApplication springApplication = new SpringApplication(LampWatcher.class);
		springApplication.setBanner(new LampWatcherBanner());
		springApplication.addListeners(new ApplicationPidFileWriter(PID_FILE_NAME));
		springApplication.run(args);
	}

}
