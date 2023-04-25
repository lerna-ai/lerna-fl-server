package ai.lerna.flapi.entity.converter;

import ai.lerna.flapi.entity.LernaAppMetadata;

public class LernaAppMetadataConverter extends BaseAttributeConverter<LernaAppMetadata, String> {

	@Override
	public String convertToDatabaseColumn(LernaAppMetadata attribute) {
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
	public LernaAppMetadata convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return new LernaAppMetadata();
		}
		try {
			return mapper.readValue(dbData, LernaAppMetadata.class);
		} catch (Throwable e) {
			logger.error("Failed to convertToEntityAttribute", e);
			throw new RuntimeException("Failed to convertToEntityAttribute");
		}
	}
}
