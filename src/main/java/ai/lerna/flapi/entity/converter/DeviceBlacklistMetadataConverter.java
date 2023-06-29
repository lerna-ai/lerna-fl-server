package ai.lerna.flapi.entity.converter;

import ai.lerna.flapi.entity.DeviceBlacklistMetadata;

public class DeviceBlacklistMetadataConverter extends BaseAttributeConverter<DeviceBlacklistMetadata, String> {

	@Override
	public String convertToDatabaseColumn(DeviceBlacklistMetadata attribute) {
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
	public DeviceBlacklistMetadata convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return new DeviceBlacklistMetadata();
		}
		try {
			return mapper.readValue(dbData, DeviceBlacklistMetadata.class);
		} catch (Throwable e) {
			logger.error("Failed to convertToEntityAttribute", e);
			throw new RuntimeException("Failed to convertToEntityAttribute");
		}
	}
}
