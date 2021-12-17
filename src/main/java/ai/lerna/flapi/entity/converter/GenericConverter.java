package ai.lerna.flapi.entity.converter;

public class GenericConverter<T> extends BaseAttributeConverter<T, String> {
	private Class<T> getGenericClass() {
		return (Class<T>) getClass();
	}

	@Override
	public String convertToDatabaseColumn(T attribute) {
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
	public T convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			try {
				return getGenericClass().getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				return null;
			}
		}
		try {
			return mapper.readValue(dbData, getGenericClass());
		} catch (Throwable e) {
			logger.error("Failed to convertToEntityAttribute", e);
			throw new RuntimeException("Failed to convertToEntityAttribute");
		}
	}
}
