package lamp.collector.app.config;

import lamp.collector.app.support.jpa.service.JpaCollectionTargetService;
import lamp.collector.core.service.CollectionTargetService;
import lamp.common.utils.assembler.SmartAssembler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ CollectorProperties.class })
public class CollectorConfig {

	@Bean
	public SmartAssembler smartAssembler() {
		return new SmartAssembler();
	}

	@Bean
	@ConditionalOnMissingBean
//	@ConditionalOnProperty(name = "spring.datasource.url")
	public CollectionTargetService collectionTargetJpaService() {
		return new JpaCollectionTargetService();
	}
	
}
