package lamp.admin.web.base.service;

public interface LogEventPublisher {
    void info(String logEventGroupId, String msg);
}
