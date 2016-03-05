package lamp.collector.core.config;

import lamp.admin.utils.assembler.SmartAssembler;
import lamp.collector.core.service.CollectionTargetJpaService;
import lamp.collector.core.service.CollectionTargetService;
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
		return new CollectionTargetJpaService();
	}
	
}
