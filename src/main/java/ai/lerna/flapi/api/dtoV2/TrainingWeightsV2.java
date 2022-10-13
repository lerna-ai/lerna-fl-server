package ai.lerna.flapi.api.dtoV2;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Schema(description = "Aggregated weights")
public class TrainingWeightsV2 implements Serializable {

	private Map<String, List<Double>> weights; // (prediction(lenra_job), weights(lerna_job))
	private Long mlId; // database lerna_job.ml_id
	private String mlName; // database lerna_ml.model
	private BigDecimal accuracy = new BigDecimal(0.0); // database lerna_ml.accuracy

	public TrainingWeightsV2() {
		// for serialisation/deserialization
	}

	public TrainingWeightsV2(Builder builder) {
		mlId = builder.mlId;
		weights = builder.weights;
		mlName = builder.mlName;
		accuracy = builder.accuracy;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(TrainingWeightsV2 copy) {
		return newBuilder()
			.setMlId(copy.getMlId())
			.setWeights(copy.getWeights())
			.setMlName(copy.getMlName())
			.setAccuracy(copy.getAccuracy());
	}

	public Long getMlId() {
		return mlId;
	}


	public Map<String, List<Double>> getWeights() {
		return weights;
	}

	public String getMlName() {
		return mlName;
	}

	public BigDecimal getAccuracy() {
		return accuracy;
	}

	public static final class Builder {
		private Map<String, List<Double>> weights;
		private Long mlId;
		private String mlName;
		private BigDecimal accuracy;

		private Builder() {
			weights = new HashMap<>();
		}

		public Builder setWeights(Map<String, List<Double>> weights) {
			this.weights = weights;
			return this;
		}

		public Builder setMlId(Long mlId) {
			this.mlId = mlId;
			return this;
		}

		public Builder setMlName(String mlName) {
			this.mlName = mlName;
			return this;
		}

		public Builder setAccuracy(BigDecimal accuracy) {
			this.accuracy = accuracy;
			return this;
		}

		public TrainingWeightsV2 build() {
			return new TrainingWeightsV2(this);
		}
	}
}
