package lamp.server.aladin.admin.config;


import lamp.server.aladin.LampServer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(basePackages = {"lamp.server.aladin.admin.repository", "lamp.server.aladin.core.repository"})
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EntityScan(basePackageClasses = { LampServer.class, Jsr310JpaConverters.class })
@EnableTransactionManagement
@Configuration
public class DatabaseConfig {


}
