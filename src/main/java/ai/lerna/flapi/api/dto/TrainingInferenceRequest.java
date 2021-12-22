package ai.lerna.flapi.api.dto;

import java.io.Serializable;
import java.util.List;

public class TrainingInferenceRequest implements Serializable {

	
	private long deviceId;
	private long version;
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

	public List<TrainingInference> getPrediction() {
		return trainingInference;
	}

	public void setPrediction(List<TrainingInference> trainingInference) {
		this.trainingInference = trainingInference;
	}
}
