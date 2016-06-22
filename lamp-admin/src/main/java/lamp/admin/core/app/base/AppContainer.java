package lamp.admin.core.app.base;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lamp.admin.core.app.docker.DockerAppContainer;
import lamp.admin.core.app.simple.SimpleAppContainer;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes({
	@JsonSubTypes.Type(value = SimpleAppContainer.class, name = AppContainerType.Names.SIMPLE),
	@JsonSubTypes.Type(value = DockerAppContainer.class, name = AppContainerType.Names.DOCKER)
})
public interface AppContainer {

}
