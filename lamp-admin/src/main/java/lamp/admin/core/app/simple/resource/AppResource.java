package lamp.admin.core.app.simple.resource;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lamp.admin.domain.resource.repo.model.AppRepoType;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes({
	@JsonSubTypes.Type(value = ArtifactAppResource.class, name = AppResourceType.Values.ARTIFACT),
	@JsonSubTypes.Type(value = UrlAppResource.class, name = AppResourceType.Values.URL)
})
public interface AppResource {

}
