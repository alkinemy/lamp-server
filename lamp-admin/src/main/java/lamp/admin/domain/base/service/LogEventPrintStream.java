package lamp.admin.domain.base.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class LogEventPrintStream extends PrintStream {

    public LogEventPrintStream(File file) throws IOException {
        super(file);
    }

}
