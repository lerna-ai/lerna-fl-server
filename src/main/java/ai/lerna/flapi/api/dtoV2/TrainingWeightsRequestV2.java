package ai.lerna.flapi.api.dtoV2;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

@Schema(description = "Get individual noisy weights")
public class TrainingWeightsRequestV2 implements Serializable {

	private long jobId;
	private long deviceId;
	private long version;
	private long datapoints;
	private List<Double> deviceWeights;

	public TrainingWeightsRequestV2() {
		// for serialisation/deserialization
	}

	public long getJobId() {
		return jobId;
	}

	public long getDeviceId() {
		return deviceId;
	}

	public long getVersion() {
		return version;
	}

	public long getDatapoints() {
		return datapoints;
	}

	public List<Double> getDeviceWeights() {
		return deviceWeights;
	}

	public TrainingWeightsRequestV2(Builder builder) {
		jobId = builder.jobId;
		deviceId = builder.deviceId;
		version = builder.version;
		datapoints = builder.datapoints;
		deviceWeights = builder.deviceWeights;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(TrainingWeightsRequestV2 copy) {
		return newBuilder()
			.setJobId(copy.jobId)
			.setDeviceId(copy.deviceId)
			.setVersion(copy.version)
			.setDatapoints(copy.datapoints)
			.setDeviceWeights(copy.deviceWeights);
	}

	public static final class Builder {
		private long jobId;
		private long deviceId;
		private long version;
		private long datapoints;
		private List<Double> deviceWeights;

		public Builder setJobId(long jobId) {
			this.jobId = jobId;
			return this;
		}

		public Builder setDeviceId(long deviceId) {
			this.deviceId = deviceId;
			return this;
		}

		public Builder setDatapoints(long datapoints) {
			this.datapoints = datapoints;
			return this;
		}

		public Builder setVersion(long version) {
			this.version = version;
			return this;
		}

		public Builder setDeviceWeights(List<Double> deviceWeights) {
			this.deviceWeights = deviceWeights;
			return this;
		}

		public TrainingWeightsRequestV2 build() {
			return new TrainingWeightsRequestV2(this);
		}
	}
}
