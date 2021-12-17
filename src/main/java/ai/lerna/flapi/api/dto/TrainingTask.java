package ai.lerna.flapi.api.dto;

import ai.lerna.flapi.entity.LernaMLParameters;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

@Schema(description = "New training configuration")
public class TrainingTask implements Serializable {

	private List<Long> jobId;
	private int deviceId;
	private LernaMLParameters lernaMLParameters;

	public List<Long> getJobId() {
		return jobId;
	}

	public void setJobId(List<Long> jobId) {
		this.jobId = jobId;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public LernaMLParameters getLernaMLParameters() {
		return lernaMLParameters;
	}

	public void setLernaMLParameters(LernaMLParameters lernaMLParameters) {
		this.lernaMLParameters = lernaMLParameters;
	}
}
