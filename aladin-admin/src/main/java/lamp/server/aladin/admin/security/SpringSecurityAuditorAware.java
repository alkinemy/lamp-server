package lamp.server.aladin.admin.security;

import lamp.server.aladin.LampConstants;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;


@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        String userName = SecurityUtils.getCurrentUserLogin();
        return (userName != null ? userName : LampConstants.SYSTEM_ACCOUNT);
    }

}
