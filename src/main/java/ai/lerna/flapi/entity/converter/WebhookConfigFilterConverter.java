package ai.lerna.flapi.entity.converter;

import ai.lerna.flapi.entity.WebhookConfigFilter;

public class WebhookConfigFilterConverter extends BaseAttributeConverter<WebhookConfigFilter, String> {

	@Override
	public String convertToDatabaseColumn(WebhookConfigFilter attribute) {
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
	public WebhookConfigFilter convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return new WebhookConfigFilter();
		}
		try {
			return mapper.readValue(dbData, WebhookConfigFilter.class);
		} catch (Throwable e) {
			logger.error("Failed to convertToEntityAttribute", e);
			throw new RuntimeException("Failed to convertToEntityAttribute");
		}
	}
}
