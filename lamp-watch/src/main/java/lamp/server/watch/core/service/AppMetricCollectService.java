package lamp.server.watch.core.service;

import lamp.server.aladin.utils.StringUtils;
import lamp.server.watch.core.domain.WatchedApp;
import lamp.server.watch.core.service.metrics.MetricsAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AppMetricCollectService {


	private RestTemplate restTemplate = new RestTemplate();

	@Autowired(required = false)
	private List<AppMetricsExportService> appMetricsExportServices;


	@Async
	public void collectMetrics(WatchedApp watchedApp) {
		String url = watchedApp.getMetricsUrl();
		log.debug("app = {}, url = {}", watchedApp.getId(), url);
		if (StringUtils.isNotBlank(url)) {
			Map<String, Object> metrics = getRestTemplate(watchedApp).getForObject(url, LinkedHashMap.class);

			Map<String, Object> assembledMetrics = assembleMetrics(watchedApp, metrics);
			log.debug("assembledMetrics = {}", assembledMetrics);
			Map<String, String> tags = new LinkedHashMap<>();

			if (appMetricsExportServices != null) {
				for (AppMetricsExportService appMetricsExportService : appMetricsExportServices) {
					try {
						appMetricsExportService.exportMetrics(watchedApp, assembledMetrics, tags);
					} catch(Throwable e) {
						log.warn("Export Metrics failed", e);
					}
				}
			}
		}
	}

	protected RestTemplate getRestTemplate(WatchedApp watchedApp) {
		return restTemplate;
	}

	protected Map<String, Object> assembleMetrics(WatchedApp watchedApp, Map<String, Object> metrics) {
		MetricsAssembler metricsAssembler = null;
		return metricsAssembler.assemble(watchedApp, metrics);
	}

}
