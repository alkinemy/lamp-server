package lamp.collector.core.loader;

import lamp.collector.core.Endpoint;
import lamp.collector.core.exception.EndpointException;


public abstract class HttpLoader {

    protected String getUrl(Endpoint endpoint) {
        if (endpoint == null) {
            throw new EndpointException("Endpoint is null");
        }
        StringBuilder url = new StringBuilder();
        url.append(endpoint.getProtocol().toString().toLowerCase()).append("://");
        url.append(endpoint.getAddress()).append(":").append(endpoint.getPort());
        url.append(endpoint.getPath());
        return url.toString();
    }
}
