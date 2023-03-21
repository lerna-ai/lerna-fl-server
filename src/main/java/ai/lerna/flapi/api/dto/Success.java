package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "Success object")
public class Success implements Serializable {

	@Schema(description = "ML Model ID")
	private long ml_id;

	@Schema(description = "Version of training task")
	private long version;

	@Schema(description = "Device ID")
	private long deviceId;

	@Schema(description = "Latest prediction")
	private String prediction;

	@Schema(description = "Success event")
	private String success;

	@Schema(description = "Latest prediction position")
	private String position;

	public long getMl_id() {
		return ml_id;
	}

	public void setMl_id(long ml_id) {
		this.ml_id = ml_id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	public String getPrediction() {
		return prediction;
	}

	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}
