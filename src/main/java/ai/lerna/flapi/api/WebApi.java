package ai.lerna.flapi.api;

import ai.lerna.flapi.api.dto.LernaApplication;
import ai.lerna.flapi.api.dto.WebDashboard;
import ai.lerna.flapi.api.dto.Webhook;
import ai.lerna.flapi.api.dto.WebhookResponse;
import ai.lerna.flapi.entity.LernaPrediction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@RequestMapping(WebApi.path)
@Tag(name = WebApi.tag)
public interface WebApi {
	String path = "/api/v1/web";
	String tag = "Lerna WEB API";

	@Operation(summary = "Get Models")
	@GetMapping("/application")
	List<LernaApplication> getApplications(@RequestHeader(name = "Authorization") String bearerToken, @RequestParam(value = "includeML", required = false, defaultValue = "false") boolean includeML) throws Exception;

	@Operation(summary = "Get dashboard data")
	@GetMapping("/dashboard")
	WebDashboard getDashboardData(@RequestHeader(name = "Authorization") String bearerToken, @RequestParam(value = "appID", required = true) long appID) throws Exception;

	@Operation(summary = "Get Active devices per week")
	@GetMapping("/dashboard/activeDevices")
	List<Map<String, BigInteger>> getActiveDevices(@RequestHeader(name = "Authorization") String bearerToken, @RequestParam(value = "appID", required = true) long appID) throws Exception;

	@Operation(summary = "Check Inference")
	@GetMapping("/inference")
	List<LernaPrediction> getInference(@RequestParam(value = "token") String token) throws Exception;

	@Operation(summary = "Download Model")
	@GetMapping("/getModel")
	void getModel(HttpServletResponse response, @RequestHeader(name = "Authorization") String bearerToken, @RequestParam(value = "appID", required = true) long appID, @RequestParam(value = "mlID", required = true) long mlID) throws Exception;

	@Operation(summary = "Update Model Dimension")
	@PostMapping("/ml/dimensions")
	ResponseEntity<String> setDimensions(@RequestHeader(name = "Authorization") String bearerToken, @RequestParam(value = "appID", required = true) long appID, @RequestParam(value = "mlID", required = true) long mlID, @RequestParam(value = "dimension", required = true) int dimension) throws Exception;

	@Operation(summary = "Get Webhooks")
	@GetMapping("/webhooks")
	WebhookResponse getWebhooks(@RequestHeader(name = "Authorization") String bearerToken, @RequestParam(value = "appID", required = true) long appID) throws Exception;

	@Operation(summary = "Submit Webhook")
	@PostMapping("/webhooks")
	ResponseEntity<String> submitWebhooks(@RequestHeader(name = "Authorization") String bearerToken, @RequestBody Webhook webhook) throws Exception;

	@Operation(summary = "Get available categories for Webhooks")
	@GetMapping("/webhooks/categories")
	Map<String, Long> getWebhookCategories(@RequestHeader(name = "Authorization") String bearerToken, @RequestParam(value = "appID", required = true) long appID) throws Exception;
}
