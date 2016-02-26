package lamp.watch.server.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "lamp_watched_app")
public class WatchedApp {

	private String id;

	private String hostname;
	private String agentId;
	private String artifactId;

	private String healthType;
	private String healthUrl;
	private String metricsType;
	private String metricsUrl;

}
