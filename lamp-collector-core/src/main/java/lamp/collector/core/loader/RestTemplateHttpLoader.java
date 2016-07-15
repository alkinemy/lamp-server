package lamp.collector.core.loader;

import lamp.collector.core.Endpoint;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


public abstract class RestTemplateHttpLoader extends HttpLoader {

    protected RestTemplate getRestTemplate(Endpoint endpoint) {
        int timeoutSeconds = endpoint.getTimeoutSeconds();
        int connectTimeout = timeoutSeconds * 1000;
        int connectionRequestTimeout = timeoutSeconds * 1000;
        int readTimeout = timeoutSeconds * 1000;
        return newRestTemplate(connectTimeout, connectionRequestTimeout, readTimeout);
    }

    protected RestTemplate newRestTemplate(int connectTimeout, int connectionRequestTimeout, int readTimeout) {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(connectTimeout);
        clientHttpRequestFactory.setConnectionRequestTimeout(connectionRequestTimeout);
        clientHttpRequestFactory.setReadTimeout(readTimeout);

        return new RestTemplate(clientHttpRequestFactory);
    }

}
