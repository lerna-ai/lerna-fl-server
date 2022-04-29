package ai.lerna.flapi.api;

import ai.lerna.flapi.api.dto.LernaApplication;
import ai.lerna.flapi.api.dto.WebDashboard;
import ai.lerna.flapi.entity.LernaPrediction;
import ai.lerna.flapi.entity.LernaUser;
import ai.lerna.flapi.manager.UserManager;
import ai.lerna.flapi.manager.WebManager;
import ai.lerna.flapi.validation.TrainingApiValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

@RestController
public class WebApiImpl implements WebApi {

	private final WebManager webManager;
	private final UserManager userManager;
	private final TrainingApiValidator validator;

	@Autowired
	public WebApiImpl(WebManager webManager, UserManager userManager, TrainingApiValidator validator) {
		this.webManager = webManager;
		this.userManager = userManager;
		this.validator = validator;
	}

	@Override
	public List<LernaApplication> getApplications(String bearerToken, boolean includeML) throws Exception {
		validator.tokenValidation(bearerToken);
		LernaUser user = userManager.getProfile(bearerToken);
		return webManager.getApplications(user.getId(), includeML);
	}

	@Override
	public WebDashboard getDashboardData(String bearerToken, long appID) throws Exception {
		validator.tokenValidation(bearerToken);
		LernaUser user = userManager.getProfile(bearerToken);
		return webManager.getDashboardData(user.getId(), appID);
	}

	@Override
	public List<Map<String, BigInteger>> getActiveDevices(String bearerToken, long appId) throws Exception {
		validator.tokenValidation(bearerToken);
		LernaUser user = userManager.getProfile(bearerToken);
		return webManager.getActiveDevices(user.getId(), appId);
	}

	@Override
	public List<LernaPrediction> getInference(String token) throws Exception {
		validator.tokenValidation(token);
		return webManager.getInference(token);
	}

	@Override
	public void getModel(HttpServletResponse response, String bearerToken, long appId, long mlId) throws Exception {
		validator.tokenValidation(bearerToken);
		LernaUser user = userManager.getProfile(bearerToken);
		String fileContent = webManager.getJSONFile(user.getId(), appId, mlId);
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=model-" + appId + "-" + mlId + ".json");
		response.getWriter().print(fileContent);
	}

}
