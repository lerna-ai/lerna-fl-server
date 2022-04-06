package ai.lerna.flapi.api;

import ai.lerna.flapi.api.dto.AuthRequest;
import ai.lerna.flapi.api.dto.AuthResponse;
import ai.lerna.flapi.api.dto.UserProfile;
import ai.lerna.flapi.entity.LernaUser;
import ai.lerna.flapi.manager.UserManager;
import ai.lerna.flapi.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiImpl implements UserApi {

	private final UserManager userManager;
	private final UserValidator userValidator;

	@Autowired
	public UserApiImpl(UserManager userManager, UserValidator userValidator) {
		this.userManager = userManager;
		this.userValidator = userValidator;
	}

	@Override
	public ResponseEntity<AuthResponse> createAuthenticationToken(AuthRequest authRequest) throws Exception {
		userValidator.validate(authRequest);

		return ResponseEntity.ok(userManager.createAuthenticationToken(authRequest));
	}

	@Override
	public ResponseEntity<UserProfile> getMyProfile(String token) throws Exception {
		userValidator.validate(token);
		LernaUser user = userManager.getProfile(token);

		return ResponseEntity.ok(UserProfile.newBuilder(user).build());
	}
}
