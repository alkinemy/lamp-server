package lamp.collector.app.core.config;

import lamp.collector.app.core.service.CollectionTargetService;
import lamp.collector.app.support.jpa.service.JpaCollectionTargetService;
import lamp.common.utils.assembler.SmartAssembler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
