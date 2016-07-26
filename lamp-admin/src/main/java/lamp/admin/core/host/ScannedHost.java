package lamp.admin.core.host;

import lamp.admin.domain.host.model.HostManagedStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScannedHost {

	private String query;
	private String name;
	private String address;
	private HostManagedStatus managed;

	private boolean connected;
	private Long responseTime;

}
