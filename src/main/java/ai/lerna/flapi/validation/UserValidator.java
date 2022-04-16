package ai.lerna.flapi.validation;

import ai.lerna.flapi.api.dto.AuthRequest;
import ai.lerna.flapi.api.dto.UserPasswordChange;
import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.util.Objects;

@Component
public class UserValidator {
	public void validate(AuthRequest authRequest) throws ValidationException {
		if (Objects.isNull(authRequest)) {
			throw new ValidationException("Not valid Auth Request");
		}

		if (Strings.isNullOrEmpty(authRequest.getEmail())) {
			throw new ValidationException("Email must be non empty");
		}

		if (Strings.isNullOrEmpty(authRequest.getPassword())) {
			throw new ValidationException("Password must be non empty");
		}
	}

	public void validate(String token) throws ValidationException {
		if (Strings.isNullOrEmpty(token)) {
			throw new ValidationException("Token must be non empty");
		}
	}

	public void validate(UserPasswordChange userPasswordChange) {
		if (Strings.isNullOrEmpty(userPasswordChange.getCurrent())) {
			throw new ValidationException("Current password must be non empty");
		}

		if (Strings.isNullOrEmpty(userPasswordChange.getNewPassword())) {
			throw new ValidationException("New password must be non empty");
		}

		if (Strings.isNullOrEmpty(userPasswordChange.getRetypedPassword())) {
			throw new ValidationException("New password must be non empty");
		}

		if (!userPasswordChange.getNewPassword().equals(userPasswordChange.getRetypedPassword())) {
			throw new ValidationException("New password must be same with retyped");
		}
	}
}
