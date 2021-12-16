package ai.lerna.flapi.entity.converter;

import ai.lerna.flapi.entity.LernaPrivacy;

public class LernaPrivacyConverter extends BaseAttributeConverter<LernaPrivacy, String> {

	@Override
	public String convertToDatabaseColumn(LernaPrivacy attribute) {
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
	public LernaPrivacy convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return new LernaPrivacy();
		}
		try {
			return mapper.readValue(dbData, LernaPrivacy.class);
		} catch (Throwable e) {
			logger.error("Failed to convertToEntityAttribute", e);
			throw new RuntimeException("Failed to convertToEntityAttribute");
		}
	}
}
