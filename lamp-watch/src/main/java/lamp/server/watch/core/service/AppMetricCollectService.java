package lamp.server.watch.core.service;

import lamp.admin.utils.StringUtils;
import lamp.server.watch.core.domain.WatchedApp;
import lamp.server.watch.core.domain.WatchedAppMetrics;
import lamp.server.watch.core.service.metrics.MetricsAssembler;
import lamp.server.watch.core.service.metrics.SpringBootMetricsAssembler;
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

	private SpringBootMetricsAssembler springBootMetricsAssembler = new SpringBootMetricsAssembler();

	@Autowired(required = false)
	private List<AppMetricsExportService> appMetricsExportServices;


	@Async
	public void collectMetrics(WatchedApp watchedApp) {
		String url = watchedApp.getMetricsUrl();
		log.debug("app = {}, url = {}", watchedApp.getId(), url);
		if (StringUtils.isNotBlank(url)) {
			Map<String, Object> metrics = getRestTemplate(watchedApp).getForObject(url, LinkedHashMap.class);

			long timestamp = System.currentTimeMillis();
			WatchedAppMetrics watchedAppMetrics = assembleMetrics(timestamp, watchedApp, metrics);
			log.debug("watchedAppMetrics = {}", watchedAppMetrics);

			if (appMetricsExportServices != null) {
				for (AppMetricsExportService appMetricsExportService : appMetricsExportServices) {
					try {
						appMetricsExportService.exportMetrics(watchedAppMetrics);
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

	protected WatchedAppMetrics assembleMetrics(long timestamp, WatchedApp watchedApp, Map<String, Object> metrics) {
		MetricsAssembler metricsAssembler = getMetricsAssembler(watchedApp.getMetricsType());
		return metricsAssembler.assemble(timestamp, watchedApp, metrics);
	}

	protected MetricsAssembler getMetricsAssembler(String metricsType) {
		return springBootMetricsAssembler;
	}

}
