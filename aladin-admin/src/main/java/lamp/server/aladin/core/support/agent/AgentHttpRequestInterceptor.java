package lamp.server.aladin.core.support.agent;


import lamp.server.aladin.agent.support.jwt.JwtBuilder;
import lamp.server.aladin.utils.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

public class AgentHttpRequestInterceptor implements ClientHttpRequestInterceptor {

	private final String userAgent;

	private JwtBuilder jwtBuilder = new JwtBuilder();

	public AgentHttpRequestInterceptor() {
		String version = AgentHttpRequestInterceptor.class.getPackage().getImplementationVersion();
		this.userAgent = "aladin/" + StringUtils.defaultIfBlank(version, "Unknown");
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		HttpHeaders headers = request.getHeaders();
		headers.set("User-Agent", userAgent);

		AgentRequestUser requestUser = AgentRequestUserHolder.getRequestUser();
		if (StringUtils.isNotBlank(requestUser.getSecretKey())) {
			URI uri = request.getURI();
			StringBuilder canonicalUrlSB = new StringBuilder();
			canonicalUrlSB.append(request.getMethod().name());
			canonicalUrlSB.append("&");
			canonicalUrlSB.append(uri.getPath());
			canonicalUrlSB.append("&");
			canonicalUrlSB.append(uri.getQuery());

			String issuer = requestUser.getId();
			String secret = requestUser.getSecretKey();
			String token = jwtBuilder.generateJWTToken(canonicalUrlSB.toString(), issuer, secret);
			headers.add("Authorization", "Bearer " + token);
		}


		return execution.execute(request, body);
	}

}
