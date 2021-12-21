package ai.lerna.flapi.api;

import ai.lerna.flapi.api.dto.TrainingAccuracyRequest;
import ai.lerna.flapi.api.dto.TrainingTaskResponse;
import ai.lerna.flapi.api.dto.TrainingWeightsRequest;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;
import ai.lerna.flapi.manager.FLManager;
import ai.lerna.flapi.validation.TrainingApiValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

    @Override
    @Transactional
    public TrainingTaskResponse getNewTraining(@RequestParam(value = "token") String token, Long deviceId) throws Exception {
        validator.tokenValidation(token);
        validator.deviceIdValidation(deviceId);
        return flManager.getNewTraining(token, deviceId);
    }

    @Override
    public String postDeviceWeights(String token, TrainingWeightsRequest trainingWeightsRequest) throws Exception {
        validator.tokenValidation(token);
        return flManager.saveDeviceWeights(token, trainingWeightsRequest);
    }

    @Override
    public TrainingWeightsResponse getGlobalWeights(String token, long version) throws Exception {
        validator.tokenValidation(token);
        return flManager.getGlobalWeights(token, version);
    }

    @Override
    public String postAccuracy(String token, TrainingAccuracyRequest trainingAccuracyRequest) throws Exception {
        validator.tokenValidation(token);
        return flManager.saveDeviceAccuracy(token, trainingAccuracyRequest);
    }
}
