package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.AgentMetrics;

public interface MetricsExportService {

	void exportMetrics(AgentMetrics agentMetrics);

}
