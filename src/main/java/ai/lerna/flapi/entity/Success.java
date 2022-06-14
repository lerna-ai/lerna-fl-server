package ai.lerna.flapi.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "success")
public class Success implements Serializable {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long id;

	@Column(name = "ml_id")
	private long MLId;

	@Column(name = "version")
	private long version;

	@Column(name = "device_id")
	private long deviceId;

	@Column(name = "prediction")
	private String prediction;

	@Column(name = "success")
	private String success;

	@Column(name = "timestamp")
	private Date timestamp;

	public Success() {
		// for serialisation/deserialization
	}

	public long getId() {
		return id;
	}

	public long getMLId() {
		return MLId;
	}

	public long getVersion() {
		return version;
	}

	public long getDeviceId() {
		return deviceId;
	}

	public String getPrediction() {
		return prediction;
	}

	public String getSuccess() {
		return success;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public Success(Builder builder) {
		MLId = builder.MLId;
		version = builder.version;
		deviceId = builder.deviceId;
		prediction = builder.prediction;
		success = builder.success;
		timestamp = builder.timestamp;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(Success copy) {
		return newBuilder()
				.setMLId(copy.getMLId())
				.setVersion(copy.version)
				.setDeviceId(copy.deviceId)
				.setPrediction(copy.prediction)
				.setSuccess(copy.success)
				.setTimestamp(copy.timestamp);
	}

	public static final class Builder {
		private long MLId;
		private long version;
		private long deviceId;
		private String prediction;
		private String success;
		private Date timestamp;

		public Builder setMLId(long MLId) {
			this.MLId = MLId;
			return this;
		}

		public Builder setVersion(long version) {
			this.version = version;
			return this;
		}

		public Builder setDeviceId(long deviceId) {
			this.deviceId = deviceId;
			return this;
		}

		public Builder setPrediction(String prediction) {
			this.prediction = prediction;
			return this;
		}

		public Builder setSuccess(String success) {
			this.success = success;
			return this;
		}

		public Builder setTimestamp(Date timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public Success build() {
			return new Success(this);
		}
	}

}
