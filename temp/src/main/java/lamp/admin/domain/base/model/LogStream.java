package lamp.admin.domain.base.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.File;

@Getter
@Setter
@ToString
public class LogStream {

    protected File logFile;

}
