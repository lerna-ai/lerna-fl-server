package ai.lerna.flapi.api;

import ai.lerna.flapi.api.dto.AuthRequest;
import ai.lerna.flapi.api.dto.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(UserApi.path)
@Tag(name = UserApi.tag)
public interface UserApi {
	String path = "/api/v1/user";
	String tag = "Lerna User API";
	
	@Operation(summary = "Create Authentication Token")
	@PostMapping("/authenticate")
	ResponseEntity<AuthResponse> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception;
}
