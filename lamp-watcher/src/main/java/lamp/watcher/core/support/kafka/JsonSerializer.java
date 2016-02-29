package lamp.watcher.core.support.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;


public class JsonSerializer implements Serializer<Object> {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override public void configure(Map<String, ?> configs, boolean isKey) {

	}

	@Override public byte[] serialize(String topic, Object data) {
			try {
				if (data == null) {
					return null;
				} else {
					return objectMapper.writeValueAsBytes(data);
				}
			} catch (JsonProcessingException e) {
				throw new SerializationException("Error when serializing map to byte[]", e);
			}

	}

	@Override public void close() {
		// nothing to do
	}
}
