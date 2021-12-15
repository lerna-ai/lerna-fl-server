package ai.lerna.flapi.entity.converter;

import ai.lerna.flapi.config.JacksonConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;

public abstract class BaseAttributeConverter<X, Y> implements AttributeConverter<X, Y> {
	protected final static ObjectMapper mapper = JacksonConfiguration.getNewObjectMapper();
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
