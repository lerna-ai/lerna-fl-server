package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;

@Schema(description = "Get individual noisy weights")
public class TrainingAccuracyRequest implements Serializable {

	private long mlId;
	private long deviceId;
	private long version;
	private BigDecimal accuracy;

	public long getMlId() {
		return mlId;
	}

	public void setMlId(long mlId) {
		this.mlId = mlId;
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

	public BigDecimal getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(BigDecimal accuracy) {
		this.accuracy = accuracy;
	}
}
