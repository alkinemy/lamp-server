package lamp.collector.core.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Endpoint {

    private String protocol;
    private String address;
    private Integer port;

    private String path;

    private int timeoutSeconds;

    public Endpoint(Endpoint endpoint) {
        this.protocol = endpoint.getProtocol();
        this.address = endpoint.getAddress();
        this.port = endpoint.getPort();
        this.path = endpoint.getPath();
        this.timeoutSeconds = endpoint.getTimeoutSeconds();

    }
}
