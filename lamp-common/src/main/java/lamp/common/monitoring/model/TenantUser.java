package lamp.common.monitoring.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class TenantUser implements Serializable {

	private String id;
	private String name;

	private String email;

	private String mobilePhoneNumber;

	private boolean enabled;

}
