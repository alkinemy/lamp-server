package lamp.admin.core.monitoring.service;


import lamp.admin.core.monitoring.domain.TargetMetrics;

public interface MetricsExportService {

	void exportMetrics(TargetMetrics agentMetrics);

}
