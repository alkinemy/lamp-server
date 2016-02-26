package lamp.admin.core.monitoring.service;


import lamp.admin.core.monitoring.domain.TargetServerMetrics;

public interface MetricsExportService {

	void exportMetrics(TargetServerMetrics agentMetrics);

}
