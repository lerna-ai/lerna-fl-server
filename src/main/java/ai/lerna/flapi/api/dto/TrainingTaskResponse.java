package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "New training configuration")
public class TrainingTaskResponse implements Serializable {

	private List<TrainingTask> trainingTasks;
	private Long version; // db lerna_app.version

	TrainingTaskResponse() {
		// for serialisation/deserialization
	}

	private TrainingTaskResponse(Builder builder) {
		trainingTasks = builder.trainingTasks;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(TrainingTaskResponse copy) {
		return newBuilder()
			.setTrainingTasks(copy.getTrainingTasks());
	}

	public List<TrainingTask> getTrainingTasks() {
		return trainingTasks;
	}

	public Long getVersion() {
		return version;
	}

	public static final class Builder {
		private List<TrainingTask> trainingTasks;
		private Long version; // db lerna_app.version

		private Builder() {
			trainingTasks = new ArrayList<>();
		}

		public Builder setTrainingTasks(List<TrainingTask> trainingTasks) {
			this.trainingTasks = trainingTasks;
			return this;
		}

		public Builder setVersion(Long version) {
			this.version = version;
			return this;
		}

		public TrainingTaskResponse build() {
			return new TrainingTaskResponse(this);
		}
	}
}
