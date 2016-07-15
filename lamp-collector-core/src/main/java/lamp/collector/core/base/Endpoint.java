package lamp.collector.core.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Endpoint {

    private String protocol;
    private String address;
    private int port;

    private String path;

    private int gracePeriodSeconds;
    private int intervalSeconds;
    private int maxConsecutiveFailures;

    private int timeoutSeconds;

}
