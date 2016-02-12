package lamp.server.aladin.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppUpdateSpecForm {

	private String id;
	private String name;
	private String description;

	private Boolean monitor;

}
