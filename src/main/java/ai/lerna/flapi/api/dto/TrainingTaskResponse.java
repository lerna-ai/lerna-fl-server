package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "New training configuration")
public class TrainingTaskResponse implements Serializable {

	@Schema(description = "List of training tasks")
	private List<TrainingTask> trainingTasks;

	/**
	 * Current version ot Training Task. Persists on lerna_app table as current_version
	 */
	@Schema(description = "Current version of training tasks")
	private Long version;

	TrainingTaskResponse() {
		// for serialisation/deserialization
	}

	private TrainingTaskResponse(Builder builder) {
		trainingTasks = builder.trainingTasks;
		version = builder.version;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(TrainingTaskResponse copy) {
		return newBuilder()
			.setTrainingTasks(copy.getTrainingTasks())
			.setVersion(copy.getVersion());
	}

	public List<TrainingTask> getTrainingTasks() {
		return trainingTasks;
	}

	public Long getVersion() {
		return version;
	}

	public static final class Builder {
		private List<TrainingTask> trainingTasks;
		private Long version;

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
