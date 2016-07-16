package lamp.admin.domain.host.service;

import lamp.admin.core.host.HostStatus;
import lamp.admin.domain.host.model.HostStatusCode;
import lamp.monitoring.core.metrics.MonitoringTargetMetrics;
import lamp.monitoring.core.metrics.handler.MonitoringTargetMetricsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class HostStatusUpdateHandler implements MonitoringTargetMetricsHandler {

    @Autowired
    private HostStatusEntityService hostStatusEntityService;

    @Override
    public void handle(MonitoringTargetMetrics targetMetrics) {
        HostStatus hostStatus = getHostStatus(targetMetrics);
        hostStatusEntityService.update(targetMetrics.getId(), hostStatus);
    }

    protected HostStatus getHostStatus(MonitoringTargetMetrics targetMetrics) {
        HostStatus hostStatus = new HostStatus();
        hostStatus.setLastStatusTime(new Date(targetMetrics.getTimestamp()));

        Map<String, Object> metrics = targetMetrics.getMetrics();
        if (metrics != null && !metrics.isEmpty()) {
            hostStatus.setCpuUser(getDoubleValue(metrics.get("server.cpu.user")));
            hostStatus.setCpuNice(getDoubleValue(metrics.get("server.cpu.nice")));
            hostStatus.setCpuSys(getDoubleValue(metrics.get("server.cpu.sys")));

            hostStatus.setDiskTotal(getLongValue(metrics.get("server.fileSystem.total")));
            hostStatus.setDiskUsed(getLongValue(metrics.get("server.fileSystem.used")));
            hostStatus.setDiskFree(getLongValue(metrics.get("server.fileSystem.free")));

            hostStatus.setMemTotal(getLongValue(metrics.get("server.mem.total")));
            hostStatus.setMemUsed(getLongValue(metrics.get("server.mem.used")));
            hostStatus.setMemFree(getLongValue(metrics.get("server.mem.free")));

            hostStatus.setSwapTotal(getLongValue(metrics.get("server.swap.total")));
            hostStatus.setSwapUsed(getLongValue(metrics.get("server.swap.used")));
            hostStatus.setSwapFree(getLongValue(metrics.get("server.swap.free")));

            if (hostStatus.getDiskUsedPercentage() > 70
                    || hostStatus.getMemUsedPercentage() > 80) {
                hostStatus.setStatus(HostStatusCode.DOWN);
            } else {
                hostStatus.setStatus(HostStatusCode.UP);
            }
        } else if (targetMetrics.getException() != null) {
            // FIXME 에러 종류에 따른 상태 값 분류 필요
            hostStatus.setStatus(HostStatusCode.UNKNOWN);
//			hostStatus.setStatus(HostStatusCode.OUT_OF_SERVICE);
        } else {
            hostStatus.setStatus(HostStatusCode.UNKNOWN);
        }

        return hostStatus;
    }

    protected Double getDoubleValue(Object object) {
        if (object instanceof Double) {
            return (Double) object;
        } else if (object instanceof String) {
            return Double.parseDouble((String) object);
        }
        return null;

    }
    protected Long getLongValue(Object object) {
        if (object instanceof Long) {
            return (Long) object;
        } else if (object instanceof Integer) {
            return new Long((Integer)object);
        } else if (object instanceof String) {
            return Long.parseLong((String) object);
        }
        return null;
    }
}
