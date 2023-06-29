package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.LernaAppConfig;
import ai.lerna.flapi.entity.DeviceBlacklist;
import ai.lerna.flapi.entity.LernaApp;
import ai.lerna.flapi.repository.InfrastructureRepository;
import ai.lerna.flapi.repository.LernaAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConfigManagerImpl implements ConfigManager {

	private final InfrastructureRepository infrastructureRepository;

	private final LernaAppRepository lernaAppRepository;

	@Autowired
	public ConfigManagerImpl(InfrastructureRepository infrastructureRepository, LernaAppRepository lernaAppRepository) {
		this.infrastructureRepository = infrastructureRepository;
		this.lernaAppRepository = lernaAppRepository;
	}

	@Override
	public LernaAppConfig getLernaAppConfig(String token, Long deviceId) throws Exception {
		LernaApp lernaApp = lernaAppRepository.findByToken(token).orElseThrow(() -> new Exception("Not exists Lerna App for selected token"));
		Integer trainingDataThreshold = lernaApp.getMetadata().getTrainingDataThreshold();
		Integer cleanupThreshold = lernaApp.getMetadata().getCleanupThreshold();
		Double abTest = lernaApp.getMetadata().getAbTest();
		Optional<DeviceBlacklist> deviceBlacklist = lernaApp.getDeviceBlacklists().stream().filter(f -> f.getDeviceId() == deviceId).findAny();
		if (deviceBlacklist.isPresent()) {
			if (deviceBlacklist.get().getMetadata().isDisabled()) {
				throw new Exception("Device is disabled for this app");
			}
			if (deviceBlacklist.get().getMetadata().isMlDisabled()) {
				trainingDataThreshold = Integer.MAX_VALUE;
				cleanupThreshold = 10000;
			}
			if (deviceBlacklist.get().getMetadata().isPredictionDisabled()) {
				abTest = 1.0;
			}
		}
		return LernaAppConfig.newBuilder()
				.setMpcServerUri(lernaApp.getInfrastructure().getMpcUri())
				.setFlServerUri(lernaApp.getInfrastructure().getFlUri())
				.setUploadPrefix(lernaApp.getMetadata().getUploadPrefix())
				.setLogSensorData(lernaApp.getMetadata().getLogSensorData())
				.setAbTest(abTest)
				.setCustomFeaturesSize(lernaApp.getMetadata().getCustomFeaturesSize())
				.setInputDataSize(lernaApp.getMetadata().getInputDataSize())
				.setSensorInitialDelay(lernaApp.getMetadata().getSensorInitialDelay())
				.setTrainingDataThreshold(trainingDataThreshold)
				.setTrainingSessionsThreshold(lernaApp.getMetadata().getTrainingSessionsThreshold())
				.setCleanupThreshold(cleanupThreshold)
				.build();
	}
}
