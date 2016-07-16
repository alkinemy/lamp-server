package lamp.monitoring.core.config;

import lamp.monitoring.core.base.action.notification.mms.MmsHttpSender;
import lamp.monitoring.core.base.action.notification.mms.MmsNotificationActionExecutor;
import lamp.monitoring.core.base.action.notification.sms.SmsHttpSender;
import lamp.monitoring.core.base.action.notification.sms.SmsNotificationActionExecutor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({MonitoringProperties.class})
public class MonitoringCoreConfig {

	@Bean
	@ConditionalOnProperty(name = "lamp.monitoring.sms-notifier.protocol", havingValue = "http")
	public SmsHttpSender smsHttpNotifier(MonitoringProperties monitoringProperties) {
		return new SmsHttpSender(monitoringProperties.getSmsSender());
	}

	@Bean
	@ConditionalOnProperty(name = "lamp.monitoring.mms-notifier.protocol", havingValue = "http")
	public MmsHttpSender mmsHttpNotifier(MonitoringProperties monitoringProperties) {
		return new MmsHttpSender(monitoringProperties.getMmsSender());
	}

	@Bean
	@ConditionalOnBean(SmsHttpSender.class)
	public SmsNotificationActionExecutor mmsNotificationActionExecutor(SmsHttpSender smsHttpSender) {
		return new SmsNotificationActionExecutor(smsHttpSender);
	}

	@Bean
	@ConditionalOnBean(MmsHttpSender.class)
	public MmsNotificationActionExecutor mmsNotificationActionExecutor(MmsHttpSender mmsHttpSender) {
		return new MmsNotificationActionExecutor(mmsHttpSender);
	}

}
