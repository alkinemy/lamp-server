package lamp.server.aladin.domain.client.model;

import lamp.server.aladin.domain.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Entity
public class LampClient extends BaseEntity {

	@Id
	private String id;

	@Column(name = "name", nullable = false)
	private String name;

	private String type;
	private String version;

	private String protocol;
	private String hostname;
	private String address;
	private int port = -1;

	private String secretKey;

	@Column(name = "clientTime", nullable = true)
	private Date time;

	private String healthPath;
	private String metricsPath;

}
