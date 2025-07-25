package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.AuthRequest;
import ai.lerna.flapi.api.dto.AuthResponse;
import ai.lerna.flapi.api.dto.UserPasswordChange;
import ai.lerna.flapi.entity.LernaApp;
import ai.lerna.flapi.entity.LernaUser;

import java.util.List;

public interface UserManager {

	AuthResponse createAuthenticationToken(AuthRequest authRequest) throws Exception;

	LernaUser getProfile(String token);

	List<LernaApp> getUserApps(String token);

	String changePassword(LernaUser user, UserPasswordChange userPasswordChange) throws Exception;
}
