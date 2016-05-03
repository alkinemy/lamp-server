package lamp.collector.core.service.metrics;

import com.google.common.collect.Lists;
import lamp.collector.core.model.TargetMetricsType;
import lamp.collector.metrics.loader.rest.MetricsAndTagsMetricsLoader;
import lamp.common.collector.service.MetricsLoader;
import lamp.common.collector.model.MetricsTarget;
import lamp.common.collector.model.TargetMetrics;
import lamp.collector.metrics.loader.rest.SpringBootMetricsLoader;
import lamp.common.collector.service.TargetMetricsListLoader;
import lamp.common.collector.service.TargetMetricsLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MetricsLoaderService {

	private static final int DEFAULT_CONNECT_TIMEOUT = 1000;
	private static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT = 1000;
	private static final int DEFAULT_READ_TIMEOUT = 1000;

	private Map<String, MetricsLoader> loaderMap;

	public MetricsLoaderService() {
		loaderMap = new HashMap<>();
		loaderMap.put(TargetMetricsType.SPRING_BOOT, new SpringBootMetricsLoader(newRestTemplate(DEFAULT_CONNECT_TIMEOUT, DEFAULT_CONNECTION_REQUEST_TIMEOUT,
																								 DEFAULT_READ_TIMEOUT)));
		loaderMap.put(TargetMetricsType.METRICS_AND_TAGS, new MetricsAndTagsMetricsLoader(newRestTemplate(DEFAULT_CONNECT_TIMEOUT, DEFAULT_CONNECTION_REQUEST_TIMEOUT,
																										  DEFAULT_READ_TIMEOUT)));
	}

	public List<TargetMetrics> getMetricsList(MetricsTarget metricsTarget) {
		log.debug("metricsType = {}", metricsTarget.getMetricsType());
		MetricsLoader loader = loaderMap.get(metricsTarget.getMetricsType());
		if (loader instanceof TargetMetricsLoader) {
			TargetMetrics targetMetrics = ((TargetMetricsLoader)loader).getMetrics(metricsTarget);
			return targetMetrics != null ? Lists.newArrayList(targetMetrics) : null;
		} else if (loader instanceof TargetMetricsListLoader) {
			return ((TargetMetricsListLoader) loader).getMetricsList(metricsTarget);
		}
		return null;
	}

	protected RestTemplate newRestTemplate(int connectTimeout, int connectionRequestTimeout, int readTimeout) {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(connectTimeout);
		clientHttpRequestFactory.setConnectionRequestTimeout(connectionRequestTimeout);
		clientHttpRequestFactory.setReadTimeout(readTimeout);

		return new RestTemplate(clientHttpRequestFactory);
	}

}
