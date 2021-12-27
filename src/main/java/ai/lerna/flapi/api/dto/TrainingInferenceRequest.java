package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

@Schema(description = "Training Inference request object")
public class TrainingInferenceRequest implements Serializable {

	@Schema(description = "Device ID")
	private long deviceId;

	@Schema(description = "Version of training task")
	private long version;

	@Schema(description = "List of training inferences")
	private List<TrainingInference> trainingInference;


	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public List<TrainingInference> getTrainingInference() {
		return trainingInference;
	}

	public void setTrainingInference(List<TrainingInference> trainingInference) {
		this.trainingInference = trainingInference;
	}
}
