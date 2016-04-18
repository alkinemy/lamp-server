package lamp.admin.domain.agent.model;


import lamp.admin.domain.base.model.LogStream;
import lamp.admin.domain.base.service.LogEventPrintStream;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.File;
import java.util.UUID;

@Getter
@Setter
@ToString
public class AgentInstallLogStream extends LogStream {

    private String targetServerId;
    private AgentInstallForm installForm;
    private String installedBy;
    private LogEventPrintStream printStream;
    private File logFile;

    public File getLogFile() {
        if (logFile == null) {
            logFile = new File("logs/agent-install-" + UUID.randomUUID().toString() + ".log");
        }
        return logFile;
    }
}
