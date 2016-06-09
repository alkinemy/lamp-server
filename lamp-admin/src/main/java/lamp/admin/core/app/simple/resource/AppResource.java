package lamp.admin.core.app.simple.resource;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes({
	@JsonSubTypes.Type(value = LocalAppResource.class, name = AppResourceType.Values.LOCAL),
	@JsonSubTypes.Type(value = MavenAppResource.class, name = AppResourceType.Values.MAVEN),
	@JsonSubTypes.Type(value = UrlAppResource.class, name = AppResourceType.Values.URL)
})
public interface AppResource {

}
