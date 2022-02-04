package ai.lerna.flapi.api;

import ai.lerna.flapi.entity.LernaPrediction;
import ai.lerna.flapi.manager.FLManager;
import ai.lerna.flapi.validation.TrainingApiValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WebApiImpl implements WebApi {

	private FLManager flManager;
	private TrainingApiValidator validator;

	@Autowired
	public WebApiImpl(FLManager flManager, TrainingApiValidator validator) {
		this.flManager = flManager;
		this.validator = validator;
	}

	@Override
	public List<LernaPrediction> getInference(String token) throws Exception {
		validator.tokenValidation(token);
		return flManager.getInference(token);
	}
}
