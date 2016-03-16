package lamp.admin.web.support.security;

import lamp.admin.LampAdminConstants;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;


@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        String userName = SecurityUtils.getCurrentUserLogin();
        return (userName != null ? userName : LampAdminConstants.SYSTEM_ACCOUNT);
    }

}
