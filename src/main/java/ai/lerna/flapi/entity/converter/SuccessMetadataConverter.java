package ai.lerna.flapi.entity.converter;

import ai.lerna.flapi.entity.SuccessMetadata;

public class SuccessMetadataConverter extends BaseAttributeConverter<SuccessMetadata, String> {

	@Override
	public String convertToDatabaseColumn(SuccessMetadata attribute) {
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
	public SuccessMetadata convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return new SuccessMetadata();
		}
		try {
			return mapper.readValue(dbData, SuccessMetadata.class);
		} catch (Throwable e) {
			logger.error("Failed to convertToEntityAttribute", e);
			throw new RuntimeException("Failed to convertToEntityAttribute");
		}
	}
}
