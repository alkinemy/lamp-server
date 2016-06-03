package lamp.admin.core.app.base;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes({
	@JsonSubTypes.Type(value = AppContainerType.Values.SIMPLE, name = AppContainerType.Names.SIMPLE),
	@JsonSubTypes.Type(value = AppContainerType.Values.JAR, name = AppContainerType.Names.JAR),
	@JsonSubTypes.Type(value = AppContainerType.Values.DOCKER, name = AppContainerType.Names.DOCKER)
})
public interface AppContainer {

}
