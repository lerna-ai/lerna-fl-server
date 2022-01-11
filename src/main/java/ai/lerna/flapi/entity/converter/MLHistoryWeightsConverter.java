package ai.lerna.flapi.entity.converter;

import ai.lerna.flapi.entity.MLHistoryWeights;

import java.util.Set;

public class MLHistoryWeightsConverter extends BaseAttributeConverter<Set<MLHistoryWeights>, String> {

	@Override
	public String convertToDatabaseColumn(Set<MLHistoryWeights> attribute) {
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
	public Set<MLHistoryWeights> convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}
		try {
			return mapper.readValue(dbData, Set.class);
		} catch (Throwable e) {
			logger.error("Failed to convertToEntityAttribute", e);
			throw new RuntimeException("Failed to convertToEntityAttribute");
		}
	}
}
