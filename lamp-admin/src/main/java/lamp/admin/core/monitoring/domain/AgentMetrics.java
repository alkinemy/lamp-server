package lamp.admin.core.monitoring.domain;

import lamp.admin.core.agent.domain.Agent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor(staticName = "of")
public class AgentMetrics {

	private long timestamp;

	private String id;
	private String name;

	private String type;
	private String version;

	private Map<String, Object> metrics;

	private Map<String, String> tags;

	public static AgentMetrics of(long timestamp, Agent agent, Map<String, Object> metrics, Map<String, String> tags) {
		return of(timestamp, agent.getId(), agent.getName(), agent.getType(), agent.getVersion(), metrics, tags);
	}
}
