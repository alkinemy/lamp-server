package lamp.support.kafka.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class JsonDeserializer<T> implements Deserializer<T> {

	public static final String VALUE_TYPE = "VALUE_TYPE";

	private ObjectMapper objectMapper = new ObjectMapper();
	private Class<T> classType;

	@Override public void configure(Map<String, ?> configs, boolean isKey) {
		classType = (Class<T>) configs.get(VALUE_TYPE);
	}

	@Override public T deserialize(String topic, byte[] data) {
		try {
			if (data == null) {
				return null;
			} else {
				return objectMapper.readValue(data, classType);
			}
		} catch (IOException e) {
			throw new SerializationException("Error when deserializing byte[] to object", e);
		}
	}


	@Override public void close() {
		// nothing to do
	}
}
