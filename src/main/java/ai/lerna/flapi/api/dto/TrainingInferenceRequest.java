package ai.lerna.flapi.api.dto;

import java.io.Serializable;

public class TrainingInferenceRequest implements Serializable {

	private long ml_id;
	private long deviceId;
	private long version;
	private String prediction;

	public long getMl_id() {
		return ml_id;
	}

	public void setMl_id(long ml_id) {
		this.ml_id = ml_id;
	}

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

	public String getPrediction() {
		return prediction;
	}

	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}
}
