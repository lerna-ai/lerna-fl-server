package ai.lerna.flapi.api.dto;

import ai.lerna.flapi.entity.LernaMLParameters;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Schema(description = "New training configuration")
public class TrainingTask implements Serializable {

	private Map<String, Long> jobIds; // (prediction(lenra_job), jobId(c server))
	private Long mlId; // database lerna_ml.id
	private String mlModel;// database lerna_ml.model
	private LernaMLParameters lernaMLParameters; // database lerna_ml.ml_params

	public TrainingTask() {
		// for serialisation/deserialization
	}

	public TrainingTask(Builder builder) {
		jobIds = builder.jobIds;
		mlId = builder.mlId;
		mlModel = builder.mlModel;
		lernaMLParameters = builder.lernaMLParameters;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(TrainingTask copy) {
		return newBuilder()
			.setJobIds(copy.getJobIds())
			.setMlId(copy.getMlId())
			.setMlModel(copy.getMlModel())
			.setLernaMLParameters(copy.getLernaMLParameters());
	}

	public Map<String, Long> getJobIds() {
		return jobIds;
	}

	public Long getMlId() {
		return mlId;
	}

	public String getMlModel() {
		return mlModel;
	}

	public LernaMLParameters getLernaMLParameters() {
		return lernaMLParameters;
	}

	public static final class Builder {
		private Map<String, Long> jobIds;
		private Long mlId;
		private String mlModel;
		private LernaMLParameters lernaMLParameters;

		private Builder() {
			jobIds = new HashMap<>();
		}

		public Builder setJobIds(Map<String, Long> jobIds) {
			this.jobIds = jobIds;
			return this;
		}

		public Builder setMlId(Long mlId) {
			this.mlId = mlId;
			return this;
		}

		public Builder setMlModel(String mlModel) {
			this.mlModel = mlModel;
			return this;
		}

		public Builder setLernaMLParameters(LernaMLParameters lernaMLParameters) {
			this.lernaMLParameters = lernaMLParameters;
			return this;
		}

		public TrainingTask build() {
			return new TrainingTask(this);
		}
	}
}
