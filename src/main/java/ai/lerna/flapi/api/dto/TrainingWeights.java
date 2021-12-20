package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.nd4j.linalg.api.ndarray.INDArray;

@Schema(description = "Aggregted weights")
public class TrainingWeights implements Serializable {

	private Map<String, INDArray> weights; // (prediction(lenra_job), weights(lerna_job))
	private Long mlId; // database lerna_job.ml_id
	

	public TrainingWeights() {
		// for serialisation/deserialization
	}

	public TrainingWeights(Builder builder) {
		mlId = builder.mlId;
		weights = builder.weights;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(TrainingWeights copy) {
		return newBuilder()
			.setMlId(copy.getMlId())
			.setWeights(copy.getWeights());
	}


	public Long getMlId() {
		return mlId;
	}


	public Map<String, INDArray> getWeights() {
		return weights;
	}

	public static final class Builder {
		private Map<String, INDArray> weights;
		private Long mlId;
		

		private Builder() {
			weights = new HashMap<>();
		}

		public Builder setWeights(Map<String, INDArray> weights) {
			this.weights = weights;
			return this;
		}

		public Builder setMlId(Long mlId) {
			this.mlId = mlId;
			return this;
		}

		public TrainingWeights build() {
			return new TrainingWeights(this);
		}
	}
}
