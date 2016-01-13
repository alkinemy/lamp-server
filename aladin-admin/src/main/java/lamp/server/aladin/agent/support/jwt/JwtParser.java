package lamp.server.aladin.agent.support.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lamp.server.aladin.agent.AgentErrorCode;
import lamp.server.aladin.core.exception.Exceptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

@Slf4j
public class JwtParser {

	private ObjectMapper objectMapper = new ObjectMapper();

	public JwtObject parse(String tokenString) {
		log.debug("AuthToken : {}", tokenString);
		try {
			String[] base64EncodedSegments = tokenString.split("\\.");
			String base64EncodedHeader = base64EncodedSegments[0];
			String base64EncodedClaims = base64EncodedSegments[1];
			String signature = base64EncodedSegments[2];

			byte[] headerBytes = Base64.decodeBase64(base64EncodedHeader);
			byte[] payloadBytes = Base64.decodeBase64(base64EncodedClaims);

			JwtHeader header = objectMapper.readValue(headerBytes, JwtHeader.class);
			JwtPayload payload = objectMapper.readValue(payloadBytes, JwtPayload.class);
			log.debug("payload : {}", payload);
			return JwtObject.of(header, payload, signature);
		} catch (Exception e) {
			throw Exceptions.newException(AgentErrorCode.INVALID_AUTH_TOKEN, e);
		}
	}
}
