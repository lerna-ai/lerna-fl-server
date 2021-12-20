package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Aggregated model weights")
public class TrainingWeightsResponse implements Serializable {
	// ToDo: foreach training task
	// list of weights
	// List<Task -> List<Weights>>

	private long version;
	private List<TrainingWeights> trainingWeights;

	TrainingWeightsResponse() {
		// for serialisation/deserialization
	}

	private TrainingWeightsResponse(Builder builder) {
		trainingWeights = builder.trainingWeights;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(TrainingWeightsResponse copy) {
		return newBuilder()
			.setTrainingWeights(copy.getTrainingWeights());
	}

	public List<TrainingWeights> getTrainingWeights() {
		return trainingWeights;
	}

	public Long getVersion() {
		return version;
	}

	public static final class Builder {
		private List<TrainingWeights> trainingWeights;
		private Long version; // db lerna_app.version

		private Builder() {
			trainingWeights = new ArrayList<>();
		}

		public Builder setTrainingWeights(List<TrainingWeights> trainingWeights) {
			this.trainingWeights = trainingWeights;
			return this;
		}

		public Builder setVersion(Long version) {
			this.version = version;
			return this;
		}

		public TrainingWeightsResponse build() {
			return new TrainingWeightsResponse(this);
		}
	}

}
