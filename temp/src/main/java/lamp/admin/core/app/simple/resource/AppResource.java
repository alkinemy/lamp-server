package lamp.admin.core.app.simple.resource;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes({
	@JsonSubTypes.Type(value = AppResourceType.Values.LOCAL, name = AppResourceType.Names.LOCAL),
	@JsonSubTypes.Type(value = AppResourceType.Values.MAVEN, name = AppResourceType.Names.MAVEN),
	@JsonSubTypes.Type(value = AppResourceType.Values.URL, name = AppResourceType.Names.URL)
})
public interface AppResource {

}
