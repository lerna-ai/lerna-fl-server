package ai.lerna.flapi.entity.converter;

import ai.lerna.flapi.entity.LernaFLParameters;

public class LernaFLConverter extends BaseAttributeConverter<LernaFLParameters, String> {

	@Override
	public String convertToDatabaseColumn(LernaFLParameters attribute) {
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
	public LernaFLParameters convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return new LernaFLParameters();
		}
		try {
			return mapper.readValue(dbData, LernaFLParameters.class);
		} catch (Throwable e) {
			logger.error("Failed to convertToEntityAttribute", e);
			throw new RuntimeException("Failed to convertToEntityAttribute");
		}
	}
}
