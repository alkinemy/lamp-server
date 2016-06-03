package lamp.admin.domain.support.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;

import java.io.IOException;

public class JsonUtils {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static String stringify(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw Exceptions.newException(LampErrorCode.JSON_PROCESS_FAILED, e);
		}
	}

	public static <T> T parse(String content, Class<T> valueType) {
		try {
			return objectMapper.readValue(content, valueType);
		} catch (JsonProcessingException e) {
			throw Exceptions.newException(LampErrorCode.JSON_PROCESS_FAILED, e);
		} catch (IOException e) {
			throw Exceptions.newException(LampErrorCode.JSON_PROCESS_FAILED, e);
		}
	}
}
