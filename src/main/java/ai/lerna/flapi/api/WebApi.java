package ai.lerna.flapi.api;

import ai.lerna.flapi.api.dto.LernaApplication;
import ai.lerna.flapi.api.dto.WebDashboard;
import ai.lerna.flapi.entity.LernaPrediction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(WebApi.path)
@CrossOrigin
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
}
