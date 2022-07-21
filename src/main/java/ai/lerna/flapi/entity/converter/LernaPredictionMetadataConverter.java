package ai.lerna.flapi.entity.converter;

import ai.lerna.flapi.entity.LernaPredictionMetadata;

public class LernaPredictionMetadataConverter extends BaseAttributeConverter<LernaPredictionMetadata, String> {

	@Override
	public String convertToDatabaseColumn(LernaPredictionMetadata attribute) {
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
	public LernaPredictionMetadata convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return new LernaPredictionMetadata();
		}
		try {
			return mapper.readValue(dbData, LernaPredictionMetadata.class);
		} catch (Throwable e) {
			logger.error("Failed to convertToEntityAttribute", e);
			throw new RuntimeException("Failed to convertToEntityAttribute");
		}
	}
}
