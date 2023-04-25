package ai.lerna.flapi.api;

import ai.lerna.flapi.api.dto.AuthRequest;
import ai.lerna.flapi.api.dto.AuthResponse;
import ai.lerna.flapi.api.dto.LernaAppConfig;
import ai.lerna.flapi.api.dto.UserApp;
import ai.lerna.flapi.api.dto.UserPasswordChange;
import ai.lerna.flapi.api.dto.UserProfile;
import ai.lerna.flapi.entity.LernaApp;
import ai.lerna.flapi.entity.LernaUser;
import ai.lerna.flapi.manager.ConfigManager;
import ai.lerna.flapi.manager.UserManager;
import ai.lerna.flapi.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class ConfigApiImpl implements ConfigApi {

	private final ConfigManager configManager;
	private final UserValidator userValidator;

	@Autowired
	public ConfigApiImpl(ConfigManager configManager, UserValidator userValidator) {
		this.configManager = configManager;
		this.userValidator = userValidator;
	}

	@Override
	public ResponseEntity<LernaAppConfig> getAppConfig(String token, Long deviceId) throws Exception {
		return ResponseEntity.ok(configManager.getLernaAppConfig(token, deviceId));
	}
}
