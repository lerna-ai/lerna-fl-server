package ai.lerna.flapi.api;

import ai.lerna.flapi.api.dto.LernaAppConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(ConfigApi.path)
@Tag(name = ConfigApi.tag)
public interface ConfigApi {
	String path = "/api/v1/config";
	String tag = "Lerna Config API";

	@Operation(summary = "Get Application Configuration")
	@GetMapping("/app")
	ResponseEntity<LernaAppConfig> getAppConfig(@RequestParam(value = "token") String token, @RequestParam(value = "deviceId") Long deviceId) throws Exception;
}
