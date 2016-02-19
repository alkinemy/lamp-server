package lamp.admin.core.support.agent;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@Slf4j
public class AgentResponseErrorHandler implements ResponseErrorHandler {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return hasError(getHttpStatusCode(response));
	}

	protected boolean hasError(HttpStatus statusCode) {
		return (statusCode.series() == HttpStatus.Series.CLIENT_ERROR ||
				statusCode.series() == HttpStatus.Series.SERVER_ERROR);
	}

	protected HttpStatus getHttpStatusCode(ClientHttpResponse response) throws IOException {
		HttpStatus statusCode;
		try {
			statusCode = response.getStatusCode();
		}
		catch (IllegalArgumentException ex) {
			throw new UnknownHttpStatusCodeException(response.getRawStatusCode(),
					response.getStatusText(), response.getHeaders(), getResponseBody(response), getCharset(response));
		}
		return statusCode;
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		byte[] responseBody = getResponseBody(response);

		try {
			AgentResponseError agentError = objectMapper.readValue(responseBody, AgentResponseError.class);
			throw new AgentResponseErrorException(agentError);
		} catch (AgentResponseErrorException e) {
			throw e;
		} catch (Exception e) {
			HttpStatus statusCode = getHttpStatusCode(response);
			switch (statusCode.series()) {
				case CLIENT_ERROR:
					throw new HttpClientErrorException(statusCode, response.getStatusText(),
							response.getHeaders(), getResponseBody(response), getCharset(response));
				case SERVER_ERROR:
					throw new HttpServerErrorException(statusCode, response.getStatusText(),
							response.getHeaders(), getResponseBody(response), getCharset(response));
				default:
					throw new RestClientException("Unknown status code [" + statusCode + "]");
			}
		}


	}

	protected byte[] getResponseBody(ClientHttpResponse response) {
		try {
			InputStream responseBody = response.getBody();
			if (responseBody != null) {
				return FileCopyUtils.copyToByteArray(responseBody);
			}
		}
		catch (IOException ex) {
			// ignore
		}
		return new byte[0];
	}

	private Charset getCharset(ClientHttpResponse response) {
		HttpHeaders headers = response.getHeaders();
		MediaType contentType = headers.getContentType();
		return contentType != null ? contentType.getCharSet() : null;
	}
}
