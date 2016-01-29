package lamp.server.aladin.core.service;

import lamp.server.aladin.core.domain.Agent;

import java.util.Map;

public interface MetricsExportService {

	void exportMetrics(Agent agent, Map<String, Object> metrics);

}
