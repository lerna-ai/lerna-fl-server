package ai.lerna.flapi.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.shade.serde.jackson.ndarray.NDArrayDeSerializer;
import org.nd4j.shade.serde.jackson.ndarray.NDArraySerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {
	public static final ObjectMapper getNewObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		objectMapper.registerModule(new JodaModule());
		SimpleModule module = new SimpleModule();
		module.addDeserializer(INDArray.class, new NDArrayDeSerializer());
		module.addSerializer(INDArray.class, new NDArraySerializer());
		objectMapper.registerModule(module);
		return objectMapper;
	}

	@Bean
	public ObjectMapper objectMapper() {
		return getNewObjectMapper();
	}
}
