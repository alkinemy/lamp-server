package lamp.admin.core.monitoring.service;


import lamp.admin.core.monitoring.domain.AgentMetrics;

public interface MetricsExportService {

	void exportMetrics(AgentMetrics agentMetrics);

}
