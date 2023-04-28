package ai.lerna.flapi.entity.converter;

import ai.lerna.flapi.entity.LernaUserMetadata;

public class LernaUserMetadataConverter extends BaseAttributeConverter<LernaUserMetadata, String> {

	@Override
	public String convertToDatabaseColumn(LernaUserMetadata attribute) {
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
	public LernaUserMetadata convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return new LernaUserMetadata();
		}
		try {
			return mapper.readValue(dbData, LernaUserMetadata.class);
		} catch (Throwable e) {
			logger.error("Failed to convertToEntityAttribute", e);
			throw new RuntimeException("Failed to convertToEntityAttribute");
		}
	}
}
