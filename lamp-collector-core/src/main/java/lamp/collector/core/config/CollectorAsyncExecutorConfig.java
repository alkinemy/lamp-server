package lamp.collector.core.config;


import lamp.collector.core.metrics.handler.TargetMetricsSpringAsyncExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class CollectorAsyncExecutorConfig {

	@Bean public Executor targetHealthAsyncThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setThreadNamePrefix("health-processor-");
		threadPoolTaskExecutor.setCorePoolSize(5);
		threadPoolTaskExecutor.setMaxPoolSize(20);
		return threadPoolTaskExecutor;
	}

	@Bean public Executor targetMetricsAsyncThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setThreadNamePrefix("metrics-processor-");
		threadPoolTaskExecutor.setCorePoolSize(5);
		threadPoolTaskExecutor.setMaxPoolSize(20);
		return threadPoolTaskExecutor;
	}

	@Bean
	public TargetMetricsSpringAsyncExecutor targetMetricsAsyncExecutor() {
		return new TargetMetricsSpringAsyncExecutor();
	}

}
