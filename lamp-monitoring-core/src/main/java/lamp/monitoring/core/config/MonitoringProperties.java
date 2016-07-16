package lamp.monitoring.core.config;

import lamp.monitoring.core.base.action.notification.mms.MmsSenderProperties;
import lamp.monitoring.core.base.action.notification.sms.SmsSenderProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "lamp.monitoring")
public class MonitoringProperties {

    private SmsSenderProperties smsSender;
    private MmsSenderProperties mmsSender;

}
