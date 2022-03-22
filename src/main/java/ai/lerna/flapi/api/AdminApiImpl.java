package ai.lerna.flapi.api;

import ai.lerna.flapi.manager.FLManager;
import ai.lerna.flapi.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;

@RestController
public class AdminApiImpl implements AdminApi {

	@Value("${app.config.admin.token:ace6b178-44e5-4e86-bff7-f37199247364}")
	private String adminToken;

	private final StorageService storageService;
	private final FLManager flManager;

	@Autowired
	public AdminApiImpl(StorageService storageService, FLManager flManager) {
		this.storageService = storageService;
		this.flManager = flManager;
	}

	@Override
	public String storagePersist(String token) throws Exception {
		tokenValidation(token);
		storageService.persistOnRedis();
		return "OK";
	}

	@Override
	public String storageRetrieve(String token) throws Exception {
		tokenValidation(token);
		storageService.retrieveFromRedis();
		return "OK";
	}

	@Override
	public String storageCleanup(String token) throws Exception {
		tokenValidation(token);
		storageService.cleanupRedis();
		return "OK";
	}

	@Override
	public String prepareNewTraining(String token) throws Exception {
		tokenValidation(token);
		flManager.prepareTrainingTasks();
		return "OK";
	}

	@Override
	public String replaceAllJobs(String token) throws Exception {
		tokenValidation(token);
		flManager.replaceAllJobs();
		return "OK";
	}

	@Override
	public String replaceJob(String token, String appToken) throws Exception {
		tokenValidation(token);
		flManager.replaceJobs(appToken);
		return "OK";
	}

	void tokenValidation(String token) throws ValidationException {
		if (token.isEmpty()) {
			throw new ValidationException("Not valid token");
		} else if (!token.equals(adminToken)) {
			throw new ValidationException("Not verified token");
		}

	}
}