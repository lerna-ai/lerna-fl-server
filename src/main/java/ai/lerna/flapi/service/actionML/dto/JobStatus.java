package ai.lerna.flapi.service.actionML.dto;

import com.google.gson.GsonBuilder;
import org.joda.time.DateTime;

import java.io.Serializable;

public class JobStatus implements Serializable {
	private String jobId;
	private EngineAlgorithmIndicator status;
	private String comment;
	private DateTime createdAt;
	private DateTime completedAt;

	JobStatus() {
		// for serialisation/deserialization
	}

	private JobStatus(Builder builder) {
		jobId = builder.jobId;
		status = builder.status;
		comment = builder.comment;
		createdAt = builder.createdAt;
		completedAt = builder.completedAt;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(JobStatus copy) {
		return newBuilder()
				.setJobId(copy.getJobId())
				.setStatus(copy.getStatus())
				.setComment(copy.getComment())
				.setCreatedAt(copy.getCreatedAt())
				.setCompletedAt(copy.getCompletedAt());
	}

	public String getJobId() {
		return jobId;
	}

	public EngineAlgorithmIndicator getStatus() {
		return status;
	}

	public String getComment() {
		return comment;
	}

	public DateTime getCreatedAt() {
		return createdAt;
	}

	public DateTime getCompletedAt() {
		return completedAt;
	}

	@Override
	public String toString() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		return gsonBuilder.create().toJson(this);
	}

	public static final class Builder {
		private String jobId;
		private EngineAlgorithmIndicator status;
		private String comment;
		private DateTime createdAt;
		private DateTime completedAt;

		private Builder() {
		}

		public Builder setJobId(String jobId) {
			this.jobId = jobId;
			return this;
		}

		public Builder setStatus(EngineAlgorithmIndicator status) {
			this.status = status;
			return this;
		}

		public Builder setStatus(String status) {
			this.status = EngineAlgorithmIndicator.newBuilder().setName(status).build();
			return this;
		}

		public Builder setComment(String comment) {
			this.comment = comment;
			return this;
		}

		public Builder setCreatedAt(DateTime createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public Builder setCompletedAt(DateTime completedAt) {
			this.completedAt = completedAt;
			return this;
		}

		public JobStatus build() {
			return new JobStatus(this);
		}
	}
}
