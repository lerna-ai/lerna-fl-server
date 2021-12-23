package ai.lerna.flapi.entity.converter;

import org.nd4j.linalg.api.ndarray.INDArray;

public class INDArrayConverter extends BaseAttributeConverter<INDArray, String> {

	@Override
	public String convertToDatabaseColumn(INDArray attribute) {
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
	public INDArray convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}
		try {
			return mapper.readValue(dbData, INDArray.class);
		} catch (Throwable e) {
			logger.error("Failed to convertToEntityAttribute", e);
			throw new RuntimeException("Failed to convertToEntityAttribute");
		}
	}
}
