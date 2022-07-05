package ai.lerna.flapi.api;

import ai.lerna.flapi.api.dto.AuthRequest;
import ai.lerna.flapi.api.dto.AuthResponse;
import ai.lerna.flapi.api.dto.UserApp;
import ai.lerna.flapi.api.dto.UserPasswordChange;
import ai.lerna.flapi.api.dto.UserProfile;
import ai.lerna.flapi.entity.LernaApp;
import ai.lerna.flapi.entity.LernaUser;
import ai.lerna.flapi.manager.UserManager;
import ai.lerna.flapi.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
		List<UserApp> apps = userManager.getUserApps(token).stream().map(convert).collect(Collectors.toList());

		return ResponseEntity.ok(UserProfile.newBuilder(user)
				.setApps(apps)
				.build());
	}

	@Override
	public ResponseEntity<String> changePassword(String token, UserPasswordChange userPasswordChange) throws Exception {
		userValidator.validate(token);
		userValidator.validate(userPasswordChange);
		LernaUser user = userManager.getProfile(token);

		return ResponseEntity.ok(userManager.changePassword(user, userPasswordChange));
	}

	private final Function<LernaApp, UserApp> convert = lernaApp -> UserApp.newBuilder()
			.setId(lernaApp.getId())
			.setName(lernaApp.getName())
			.build();
}
