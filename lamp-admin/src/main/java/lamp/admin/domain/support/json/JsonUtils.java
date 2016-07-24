package lamp.admin.domain.support.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lamp.admin.domain.base.exception.Exceptions;
import lamp.admin.domain.base.exception.LampErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonUtils {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static String stringify(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.warn(String.format("Object to Json failed : %s", object), e);
			throw Exceptions.newException(LampErrorCode.JSON_PROCESS_FAILED, e);
		}
	}

	public static <T> T parse(String content, Class<T> valueType) {
		try {
			return objectMapper.readValue(content, valueType);
		} catch (JsonProcessingException e) {
			log.warn(String.format("Json to Object failed : %s, %s", content, valueType), e);
			throw Exceptions.newException(LampErrorCode.JSON_PROCESS_FAILED, e);
		} catch (IOException e) {
			log.warn(String.format("Json to Object failed : %s, %s", content, valueType), e);
			throw Exceptions.newException(LampErrorCode.JSON_PROCESS_FAILED, e);
		}
	}
}
