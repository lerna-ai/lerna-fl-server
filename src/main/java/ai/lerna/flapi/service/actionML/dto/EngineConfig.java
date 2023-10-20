package ai.lerna.flapi.service.actionML.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EngineConfig implements Serializable {
	private Engine engineParams;
	private List<JobStatus> jobStatuses;

	EngineConfig() {
		// for serialisation/deserialization
	}

	private EngineConfig(Builder builder) {
		engineParams = builder.engineParams;
		jobStatuses = builder.jobStatuses;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(EngineConfig copy) {
		return newBuilder()
				.setEngineParams(copy.getEngineParams())
				.setJobStatuses(copy.getJobStatuses());
	}

	public Engine getEngineParams() {
		return engineParams;
	}

	public List<JobStatus> getJobStatuses() {
		return jobStatuses;
	}

	public static final class Builder {
		private Engine engineParams;
		private List<JobStatus> jobStatuses;

		private Builder() {
			jobStatuses = new ArrayList<>();
		}

		public Builder setEngineParams(Engine engineParams) {
			this.engineParams = engineParams;
			return this;
		}

		public Builder setJobStatuses(List<JobStatus> jobStatuses) {
			this.jobStatuses = jobStatuses;
			return this;
		}

		public EngineConfig build() {
			return new EngineConfig(this);
		}
	}
}
