package ai.lerna.flapi.api.dto;

import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.Serializable;

public class DeviceWeights implements Serializable {
	private Long dataPoints;
	private INDArray weights;

	public DeviceWeights() {
		// for serialisation/deserialization
	}

	public DeviceWeights(Builder builder) {
		dataPoints = builder.dataPoints;
		weights = builder.weights;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(DeviceWeights copy) {
		return newBuilder()
			.setDataPoints(copy.dataPoints)
			.setWeights(copy.weights);
	}

	public Long getDataPoints() {
		return dataPoints;
	}

	public INDArray getWeights() {
		return weights;
	}

	public static final class Builder {
		private Long dataPoints;
		private INDArray weights;

		public Builder setDataPoints(Long dataPoints) {
			this.dataPoints = dataPoints;
			return this;
		}

		public Builder setWeights(INDArray weights) {
			this.weights = weights;
			return this;
		}

		public DeviceWeights build() {
			return new DeviceWeights(this);
		}
	}
}
