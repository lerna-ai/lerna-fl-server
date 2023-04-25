package ai.lerna.flapi.manager;

import ai.lerna.flapi.api.dto.LernaAppConfig;
import ai.lerna.flapi.entity.LernaApp;
import ai.lerna.flapi.repository.InfrastructureRepository;
import ai.lerna.flapi.repository.LernaAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
		return LernaAppConfig.newBuilder()
				.setMpcServerUri(lernaApp.getInfrastructure().getMpcUri())
				.setFlServerUri(lernaApp.getInfrastructure().getFlUri())
				.setUploadPrefix(lernaApp.getMetadata().getUploadPrefix())
				.setLogSensorData(lernaApp.getMetadata().getLogSensorData())
				.setAbTest(lernaApp.getMetadata().getAbTest())
				.build();
	}
}
