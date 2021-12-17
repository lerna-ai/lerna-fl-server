package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;

@Schema(description = "Get individual noisy weights")
public class TrainingAccuracyRequest implements Serializable {

	private long ml_id;
	private int deviceId;
	private BigDecimal accuracy;

	public long getMLId() {
		return ml_id;
	}

	public void setMLId(long ml_id) {
		this.ml_id = ml_id;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public BigDecimal getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(BigDecimal accuracy) {
		this.accuracy = accuracy;
	}

}
