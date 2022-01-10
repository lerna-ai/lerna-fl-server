package ai.lerna.flapi.validation;

import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.util.Objects;

@Component
public class TrainingApiValidator {
	public void tokenValidation(String token) throws ValidationException {
		// ToDo: Add token validation, isEmpty already checked because token is required field.
		if (token.isEmpty()) {
			throw new ValidationException("Not valid token");
		}

	}

	public void deviceIdValidation(Long deviceId) throws ValidationException {
		if (Objects.isNull(deviceId) || deviceId.equals(0L)) {
			throw new ValidationException("Not valid device ID");
		}
	}
}
