package ai.lerna.flapi.api;

import ai.lerna.flapi.api.dto.LernaApplication;
import ai.lerna.flapi.entity.LernaPrediction;
import ai.lerna.flapi.entity.LernaUser;
import ai.lerna.flapi.manager.FLManager;
import ai.lerna.flapi.manager.UserManager;
import ai.lerna.flapi.validation.TrainingApiValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WebApiImpl implements WebApi {

	private final FLManager flManager;
	private final UserManager userManager;
	private final TrainingApiValidator validator;

	@Autowired
	public WebApiImpl(FLManager flManager, UserManager userManager, TrainingApiValidator validator) {
		this.flManager = flManager;
		this.userManager = userManager;
		this.validator = validator;
	}

	@Override
	public List<LernaApplication> getApplications(String bearerToken, boolean includeML) throws Exception {
		validator.tokenValidation(bearerToken);
		LernaUser user = userManager.getProfile(bearerToken);
		return flManager.getApplications(user.getId(), includeML);
	}

	@Override
	public List<LernaPrediction> getInference(String token) throws Exception {
		validator.tokenValidation(token);
		return flManager.getInference(token);
	}
}
