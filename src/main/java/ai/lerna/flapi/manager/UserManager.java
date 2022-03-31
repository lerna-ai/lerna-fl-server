package ai.lerna.flapi.manager;


import ai.lerna.flapi.api.dto.AuthRequest;
import ai.lerna.flapi.api.dto.AuthResponse;

public interface UserManager {

	AuthResponse createAuthenticationToken(AuthRequest authRequest) throws Exception;
}
