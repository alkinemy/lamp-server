package lamp.server.aladin.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class LocalAppFileUploadForm {

	private String name;
	private String description;

	@NotEmpty
	private String groupId;
	@NotEmpty
	private String artifactId;
	@NotEmpty
	private String version;

	private String filename;

	@NotNull
	private MultipartFile uploadFile;

}
