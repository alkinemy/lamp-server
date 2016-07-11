package lamp.admin.config.web;

import lamp.admin.core.agent.AgentClient;
import lamp.admin.core.agent.AgentHttpRequestInterceptor;
import lamp.admin.core.agent.AgentResponseErrorHandler;
import lamp.admin.domain.agent.service.AgentScheduledService;
import lamp.admin.domain.host.service.HostScheduledService;
import lamp.admin.domain.monitoring.service.AlertEventService;
import lamp.admin.web.monitoring.service.AlarmService;
import lamp.collector.core.config.CollectorCoreConfig;
import lamp.monitoring.core.health.service.HealthMonitoringProcessor;
import lamp.monitoring.core.health.service.SimpleHealthMonitoringProcessor;
import lamp.monitoring.core.notification.mms.MmsHttpSender;
import lamp.monitoring.core.notification.mms.SmsHttpSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
@EnableConfigurationProperties({ServerProperties.class})
@Import({ CollectorCoreConfig.class})
public class ServerConfig {


	@Bean
	public AgentClient agentClient(ServerProperties serverProperties) {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(serverProperties.getConnectTimeout());
		clientHttpRequestFactory.setConnectionRequestTimeout(serverProperties.getConnectionRequestTimeout());
		clientHttpRequestFactory.setReadTimeout(serverProperties.getReadTimeout());

		AgentResponseErrorHandler errorHandler = new AgentResponseErrorHandler();
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
		restTemplate.setErrorHandler(errorHandler);
		restTemplate.getInterceptors().add(new AgentHttpRequestInterceptor());
		return new AgentClient(restTemplate);
	}

	@Bean
	public HealthMonitoringProcessor healthWatcher(AlarmService alarmService, AlertEventService alarmEventService) {
		return new SimpleHealthMonitoringProcessor(alarmService, alarmEventService);
	}

	@Bean
	@ConditionalOnProperty(name = "lamp.server.mms-notifier.protocol", havingValue = "http")
	public MmsHttpSender mmsHttpNotifier(ServerProperties serverProperties) {
		return new MmsHttpSender(serverProperties.getMmsSender());
	}

	@Bean
	@ConditionalOnProperty(name = "lamp.server.sms-notifier.protocol", havingValue = "http")
	public SmsHttpSender smsHttpNotifier(ServerProperties serverProperties) {
		return new SmsHttpSender(serverProperties.getSmsSender());
	}

	@Configuration
	public static class ServerSchedulingConfig implements SchedulingConfigurer {

		@Autowired
		private ServerProperties serverProperties;

		@Bean
		public HostScheduledService hostScheduledService() {
			return new HostScheduledService();
		}

		@Bean
		public AgentScheduledService agentScheduledService() {
			return new AgentScheduledService();
		}

		@Override public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
			taskRegistrar.addFixedDelayTask(() -> {
				hostScheduledService().hostMetricsMonitoring();
			}, serverProperties.getHostMetricsMonitoringInterval());

			taskRegistrar.addFixedDelayTask(() -> {
				agentScheduledService().appInstanceStatusUpdate();
			}, serverProperties.getAppInstanceStatusUpdateInterval());
		}
	}
}
