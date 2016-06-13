package lamp.admin.core.app.jar;


import com.fasterxml.jackson.annotation.JsonTypeName;
import lamp.admin.core.app.base.AppContainerType;
import lamp.admin.core.app.simple.SimpleAppContainer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonTypeName(AppContainerType.Names.SPRING_BOOT)
public class SpringBootAppContainer extends SimpleAppContainer {

	private String properties;

	private String jvmOpts;
	private String springOpts;

}
