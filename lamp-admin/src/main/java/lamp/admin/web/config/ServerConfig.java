package lamp.admin.web.config;


import lamp.collector.core.config.CollectorCoreConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ CollectorCoreConfig.class})
public class ServerConfig {


}
