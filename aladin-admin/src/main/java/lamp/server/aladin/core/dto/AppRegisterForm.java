package lamp.server.aladin.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppRegisterForm {

	private String id;
	private String name;
	private String description;

	private Long templateId;
	private String version;

	private Boolean monitor;

}
