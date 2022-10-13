package ai.lerna.flapi.api.dtoV2;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Aggregated model weights")
public class TrainingWeightsResponseV2 implements Serializable {
	// ToDo: foreach training task
	// list of weights
	// List<Task -> List<Weights>>

	private long version;
	private List<TrainingWeightsV2> trainingWeights;

	TrainingWeightsResponseV2() {
		// for serialisation/deserialization
	}

	private TrainingWeightsResponseV2(Builder builder) {
		trainingWeights = builder.trainingWeights;
		version = builder.version;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(TrainingWeightsResponseV2 copy) {
		return newBuilder()
			.setVersion(copy.getVersion())
			.setTrainingWeights(copy.getTrainingWeights());
	}

	public List<TrainingWeightsV2> getTrainingWeights() {
		return trainingWeights;
	}

	public long getVersion() {
		return version;
	}

	public static final class Builder {
		private List<TrainingWeightsV2> trainingWeights;
		private long version; // db lerna_app.version

		private Builder() {
			trainingWeights = new ArrayList<>();
		}

		public Builder setTrainingWeights(List<TrainingWeightsV2> trainingWeights) {
			this.trainingWeights = trainingWeights;
			return this;
		}

		public Builder setVersion(long version) {
			this.version = version;
			return this;
		}

		public TrainingWeightsResponseV2 build() {
			return new TrainingWeightsResponseV2(this);
		}
	}
}
