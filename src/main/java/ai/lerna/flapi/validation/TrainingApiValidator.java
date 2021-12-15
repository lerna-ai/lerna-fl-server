package ai.lerna.flapi.validation;

import org.springframework.stereotype.Component;

@Component
public class TrainingApiValidator {
	public void tokenValidation(String token) throws Exception {
		// ToDo: Add token validation, isEmpty already checked because token is required field.
		if (token.isEmpty()) {
			throw new Exception("Not valid token");
		}

	}
}
