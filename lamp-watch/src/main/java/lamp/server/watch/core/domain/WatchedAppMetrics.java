package lamp.server.watch.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor(staticName = "of")
public class WatchedAppMetrics {

	private long timestamp;
	private String id;

	private String hostname;
	private String agentId;
	private String artifactId;

	private Map<String, Object> metrics;
	private Map<String, String> tags;

	public static WatchedAppMetrics of(long timestamp, WatchedApp watchedApp, Map<String, Object> metrics, Map<String, String> tags) {
		return of(timestamp, watchedApp.getId(), watchedApp.getHostname(), watchedApp.getAgentId(), watchedApp.getArtifactId(), metrics, tags);
	}
}
