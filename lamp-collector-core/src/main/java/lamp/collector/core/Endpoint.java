package lamp.collector.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Endpoint {

    private EndpointProtocol protocol;
    private String address;
    private int port;

    private String path;

    private int gracePeriodSeconds;
    private int intervalSeconds;
    private int maxConsecutiveFailures;
    private int timeoutSeconds;

}
