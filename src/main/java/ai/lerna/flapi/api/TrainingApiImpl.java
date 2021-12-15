package ai.lerna.flapi.api;

import ai.lerna.flapi.api.dto.TrainingInitResponse;
import ai.lerna.flapi.manager.FLManager;
import ai.lerna.flapi.validation.TrainingApiValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainingApiImpl implements TrainingApi {

	private FLManager flManager;
	private TrainingApiValidator validator;

	@Autowired
	public TrainingApiImpl(FLManager flManager, TrainingApiValidator validator) {
		this.flManager = flManager;
		this.validator = validator;
	}

	public TrainingInitResponse getNewTraining(@RequestParam(value = "token") String token) throws Exception {
		validator.tokenValidation(token);
		return flManager.getNewTraining(token);
	}
}
