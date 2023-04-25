package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.LernaAppConfig;

public interface ConfigManager {
	LernaAppConfig getLernaAppConfig(String token, Long deviceId) throws Exception;
}
