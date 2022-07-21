package ai.lerna.flapi.entity;


import ai.lerna.flapi.entity.converter.LernaPredictionMetadataConverter;
import ai.lerna.flapi.entity.converter.WebhookConfigFilterConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "lerna_predictions")
public class LernaPrediction implements Serializable {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long id;

	@Column(name = "device_id")
	private long deviceId;

	@Column(name = "ml_id")
	private long MLId;

	@Column(name = "version")
	private long version;

	@Column(name = "model")
	private String model;

	@Column(name = "prediction")
	private String prediction;

	@Column(name = "metadata")
	@Convert(converter = LernaPredictionMetadataConverter.class)
	private LernaPredictionMetadata metadata = new LernaPredictionMetadata();

	@Column(name = "timestamp")
	private Date timestamp;

	public LernaPrediction() {
		// for serialisation/deserialization
	}

	public long getId() {
		return id;
	}

	public long getMLId() {
		return MLId;
	}

	public String getPrediction() {
		return prediction;
	}

	public long getDeviceId() {
		return deviceId;
	}

	public long getVersion() {
		return version;
	}

	public String getModel() {
		return model;
	}

	public LernaPredictionMetadata getMetadata() {
		return metadata;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public LernaPrediction(Builder builder) {
		deviceId = builder.deviceId;
		MLId = builder.MLId;
		version = builder.version;
		model = builder.model;
		prediction = builder.prediction;
		metadata = builder.metadata;
		timestamp = builder.timestamp;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(LernaPrediction copy) {
		return newBuilder()
			.setDeviceId(copy.deviceId)
			.setMLId(copy.MLId)
			.setVersion(copy.version)
			.setModel(copy.model)
			.setPrediction(copy.prediction)
			.setMetadata(copy.metadata)
			.setTimestamp(copy.timestamp);
	}

	public static final class Builder {
		private long deviceId;
		private long MLId;
		private long version;
		private String model;
		private String prediction;
		private LernaPredictionMetadata metadata = new LernaPredictionMetadata();
		private Date timestamp;

		public Builder setDeviceId(long deviceId) {
			this.deviceId = deviceId;
			return this;
		}

		public Builder setMLId(long MLId) {
			this.MLId = MLId;
			return this;
		}

		public Builder setModel(String model) {
			this.model = model;
			return this;
		}

		public Builder setVersion(long version) {
			this.version = version;
			return this;
		}

		public Builder setPrediction(String prediction) {
			this.prediction = prediction;
			return this;
		}

		public Builder setMetadata(LernaPredictionMetadata metadata) {
			this.metadata = metadata;
			return this;
		}

		public Builder setTimestamp(Date timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public LernaPrediction build() {
			return new LernaPrediction(this);
		}
	}

}
