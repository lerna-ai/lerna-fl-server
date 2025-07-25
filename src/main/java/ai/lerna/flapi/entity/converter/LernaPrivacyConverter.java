package ai.lerna.flapi.entity.converter;

import ai.lerna.flapi.entity.LernaPrivacyParameters;

public class LernaPrivacyConverter extends BaseAttributeConverter<LernaPrivacyParameters, String> {

	@Override
	public String convertToDatabaseColumn(LernaPrivacyParameters attribute) {
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
	public LernaPrivacyParameters convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return new LernaPrivacyParameters();
		}
		try {
			return mapper.readValue(dbData, LernaPrivacyParameters.class);
		} catch (Throwable e) {
			logger.error("Failed to convertToEntityAttribute", e);
			throw new RuntimeException("Failed to convertToEntityAttribute");
		}
	}
}
