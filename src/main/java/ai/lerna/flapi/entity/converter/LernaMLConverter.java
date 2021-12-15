package ai.lerna.flapi.entity.converter;

import ai.lerna.flapi.entity.LernaMLParameters;

public class LernaMLConverter extends BaseAttributeConverter<LernaMLParameters, String> {

	@Override
	public String convertToDatabaseColumn(LernaMLParameters attribute) {
		if (attribute == null) {
			return null;
		}
		try {
			return mapper.writeValueAsString(attribute);
		} catch (Throwable e) {
			logger.error("Failed to convertToDatabaseColumn", e);
			throw new RuntimeException("Failed to convertToDatabaseColumn");
		}
	}

	@Override
	public LernaMLParameters convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return new LernaMLParameters();
		}
		try {
			return mapper.readValue(dbData, LernaMLParameters.class);
		} catch (Throwable e) {
			logger.error("Failed to convertToEntityAttribute", e);
			throw new RuntimeException("Failed to convertToEntityAttribute");
		}
	}
}
