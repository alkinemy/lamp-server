package lamp.watcher.support;

import lamp.watcher.config.LampWatcherProperties;
import lamp.watcher.support.jwt.JwtBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

public class LampHttpRequestInterceptor implements ClientHttpRequestInterceptor {

	private final String userAgent;
	private LampWatcherProperties clientProperties;

	private JwtBuilder jwtBuilder = new JwtBuilder();

	public LampHttpRequestInterceptor(LampWatcherProperties clientProperties) {
		this.clientProperties = clientProperties;
		this.userAgent = clientProperties.getGroupId() + "/" + clientProperties.getArtifactId() + "/" + clientProperties.getVersion();
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
		throws IOException {
		HttpHeaders headers = request.getHeaders();
		headers.set("User-Agent", userAgent);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		URI uri = request.getURI();
		StringBuilder canonicalUrlSB = new StringBuilder();
		canonicalUrlSB.append(request.getMethod().name());
		canonicalUrlSB.append("&");
		canonicalUrlSB.append(uri.getPath());
		canonicalUrlSB.append("&");
		canonicalUrlSB.append(uri.getQuery());

		String issuer = clientProperties.getId();
		String secret = clientProperties.getSecretKey();
		String token = jwtBuilder.generateJWTToken(canonicalUrlSB.toString(), issuer, secret);
		headers.add("Authorization", "Bearer " + token);

		return execution.execute(request, body);
	}

}
