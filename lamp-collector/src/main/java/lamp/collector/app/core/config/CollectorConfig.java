package lamp.collector.app.core.config;

import lamp.common.utils.assembler.SmartAssembler;
import lamp.collector.app.core.service.CollectionTargetService;
import lamp.collector.app.support.jpa.service.JpaCollectionTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class CollectorConfig {

	@Bean
	public SmartAssembler smartAssembler() {
		return new SmartAssembler();
	}

	@Autowired
	private DataSource dataSource;

	@Bean
	@ConditionalOnMissingBean
//	@ConditionalOnProperty(name = "spring.datasource.url")
	public CollectionTargetService collectionTargetJpaService() {
		return new JpaCollectionTargetService();
	}
	
}
