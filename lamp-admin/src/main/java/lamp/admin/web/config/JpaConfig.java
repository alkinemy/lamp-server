package lamp.admin.web.config;


import lamp.admin.core.LampAdminCore;
import lamp.admin.web.LampAdminWeb;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(basePackageClasses = { LampAdminCore.class, LampAdminWeb.class })
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EntityScan(basePackageClasses = { LampAdminCore.class, LampAdminWeb.class, Jsr310JpaConverters.class })
@EnableTransactionManagement
@Configuration
public class JpaConfig {


}
