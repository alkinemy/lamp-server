package lamp.admin.domain.monitoring.service;


import lamp.admin.domain.monitoring.model.TargetServerMetrics;

public interface MetricsExportService {

	void exportMetrics(TargetServerMetrics agentMetrics);

}
