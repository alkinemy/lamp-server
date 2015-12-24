package lamp.server.aladin.core.service.command;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

@Getter
@Setter
@ToString
public class LampClientCreateCommand {

	@NotEmpty
	private String id;
	@NotEmpty
	private String name;

	@NotEmpty
	private String type;
	@NotEmpty
	private String version;

	private String protocol;
	private String hostname;
	private String address;
	private int port = -1;

	private String secretKey;

	private Date time;

	private String healthType;
	private String healthPath;
	private String metricsType;
	private String metricsPath;

}
