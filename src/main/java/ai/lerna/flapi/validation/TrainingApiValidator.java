package ai.lerna.flapi.validation;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TrainingApiValidator {
	public void tokenValidation(String token) throws Exception {
		// ToDo: Add token validation, isEmpty already checked because token is required field.
		if (token.isEmpty()) {
			throw new Exception("Not valid token");
		}

	}

	public void deviceIdValidation(Long deviceId) throws Exception {
		if (Objects.isNull(deviceId) || deviceId.equals(0L)) {
			throw new Exception("Not valid device ID");
		}
	}
}
