package lamp.admin.domain.app.base.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "lamp.app.spring-boot")
public class SpringBootAppProperties {

	private String shellScriptLocation = "classpath:app/spring-boot/spring-boot.sh";
}
