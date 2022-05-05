package ai.lerna.flapi.entity.converter;

import ai.lerna.flapi.entity.WebhookConfigRequest;

public class WebhookConfigRequestConverter extends BaseAttributeConverter<WebhookConfigRequest, String> {

	@Override
	public String convertToDatabaseColumn(WebhookConfigRequest attribute) {
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
	public WebhookConfigRequest convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return new WebhookConfigRequest();
		}
		try {
			return mapper.readValue(dbData, WebhookConfigRequest.class);
		} catch (Throwable e) {
			logger.error("Failed to convertToEntityAttribute", e);
			throw new RuntimeException("Failed to convertToEntityAttribute");
		}
	}
}
