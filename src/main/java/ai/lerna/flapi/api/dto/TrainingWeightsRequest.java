package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.Serializable;

@Schema(description = "Get individual noisy weights")
public class TrainingWeightsRequest implements Serializable {

	private long jobId;
	private int deviceId;
	private long version;
	private INDArray deviceWeights;

	public TrainingWeightsRequest() {
		// for serialisation/deserialization
	}

	public TrainingWeightsRequest(Builder builder) {
		jobId = builder.jobId;
		deviceId = builder.deviceId;
		version = builder.version;
		deviceWeights = builder.deviceWeights;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(TrainingWeightsRequest copy) {
		return newBuilder()
			.setJobId(copy.jobId)
			.setDeviceId(copy.deviceId)
			.setVersion(copy.version)
			.setDeviceWeights(copy.deviceWeights);
	}

	public static final class Builder {
		private long jobId;
		private int deviceId;
		private long version;
		private INDArray deviceWeights;

		public Builder setJobId(long jobId) {
			this.jobId = jobId;
			return this;
		}

		public Builder setDeviceId(int deviceId) {
			this.deviceId = deviceId;
			return this;
		}

		public Builder setVersion(long version) {
			this.version = version;
			return this;
		}

		public Builder setDeviceWeights(INDArray deviceWeights) {
			this.deviceWeights = deviceWeights;
			return this;
		}

		public TrainingWeightsRequest build() {
			return new TrainingWeightsRequest(this);
		}
	}
}
