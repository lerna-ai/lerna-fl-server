package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "Lerna Application Configuration")
public class LernaAppConfig implements Serializable {

	@Schema(description = "MPC Server URI")
	private String mpcServerUri;

	@Schema(description = "FL Server URI")
	private String flServerUri;

	@Schema(description = "Prefix for upload files")
	private String uploadPrefix;

	@Schema(description = "Enable sensor data uploading")
	private boolean uploadSensorData;

	@Schema(description = "Enable sensor logging")
	private boolean logSensorData;

	@Schema(description = "AB test percentage value")
	private Double abTest;

	@Schema(description = "Size of custom features data")
	private int customFeaturesSize;

	@Schema(description = "Size of input data")
	private int inputDataSize;

	@Schema(description = "Sensor initial delay in ms")
	private int sensorInitialDelay;

	@Schema(description = "Training number of sessions threshold")
	private Integer trainingSessionsThreshold;

	@Schema(description = "Threshold for cleanup cache memory")
	private Integer cleanupThreshold;

	public LernaAppConfig() {
		// for serialisation/deserialization
	}

	public LernaAppConfig(Builder builder) {
		mpcServerUri = builder.mpcServerUri;
		flServerUri = builder.flServerUri;
		uploadPrefix = builder.uploadPrefix;
		uploadSensorData = builder.uploadSensorData;
		logSensorData = builder.logSensorData;
		abTest = builder.abTest;
		customFeaturesSize = builder.customFeaturesSize;
		inputDataSize = builder.inputDataSize;
		sensorInitialDelay = builder.sensorInitialDelay;
		trainingSessionsThreshold = builder.trainingSessionsThreshold;
		cleanupThreshold = builder.cleanupThreshold;
	}

	public static Builder newBuilder() {
		return new LernaAppConfig.Builder();
	}

	public static Builder newBuilder(LernaAppConfig copy) {
		return newBuilder()
				.setMpcServerUri(copy.getMpcServerUri())
				.setFlServerUri(copy.getFlServerUri())
				.setUploadPrefix(copy.getUploadPrefix())
				.setUploadSensorData(copy.getUploadSensorData())
				.setLogSensorData(copy.getLogSensorData())
				.setAbTest(copy.getAbTest())
				.setCustomFeaturesSize(copy.getCustomFeaturesSize())
				.setInputDataSize(copy.getInputDataSize())
				.setSensorInitialDelay(copy.getSensorInitialDelay())
				.setTrainingSessionsThreshold(copy.getTrainingSessionsThreshold())
				.setCleanupThreshold(copy.getCleanupThreshold());
	}

	public String getMpcServerUri() {
		return mpcServerUri;
	}

	public String getFlServerUri() {
		return flServerUri;
	}

	public String getUploadPrefix() {
		return uploadPrefix;
	}

	public boolean getUploadSensorData() {
		return uploadSensorData;
	}

	public boolean getLogSensorData() {
		return logSensorData;
	}

	public Double getAbTest() {
		return abTest;
	}

	public int getCustomFeaturesSize() {
		return customFeaturesSize;
	}

	public int getInputDataSize() {
		return inputDataSize;
	}

	public int getSensorInitialDelay() {
		return sensorInitialDelay;
	}

	public Integer getTrainingSessionsThreshold() {
		return trainingSessionsThreshold;
	}

	public Integer getCleanupThreshold() {
		return cleanupThreshold;
	}

	public static final class Builder {
		private String mpcServerUri;
		private String flServerUri;
		private String uploadPrefix;
		private boolean uploadSensorData;
		private boolean logSensorData;
		private Double abTest;
		private int customFeaturesSize;
		private int inputDataSize;
		private int sensorInitialDelay;
		private Integer trainingSessionsThreshold;
		private Integer cleanupThreshold;


		private Builder() {
		}

		public Builder setMpcServerUri(String mpcServerUri) {
			this.mpcServerUri = mpcServerUri;
			return this;
		}

		public Builder setFlServerUri(String flServerUri) {
			this.flServerUri = flServerUri;
			return this;
		}

		public Builder setUploadPrefix(String uploadPrefix) {
			this.uploadPrefix = uploadPrefix;
			return this;
		}

		public Builder setUploadSensorData(boolean uploadSensorData) {
			this.uploadSensorData = uploadSensorData;
			return this;
		}

		public Builder setLogSensorData(boolean logSensorData) {
			this.logSensorData = logSensorData;
			return this;
		}

		public Builder setAbTest(Double abTest) {
			this.abTest = abTest;
			return this;
		}

		public Builder setCustomFeaturesSize(int customFeaturesSize) {
			this.customFeaturesSize = customFeaturesSize;
			return this;
		}

		public Builder setInputDataSize(int inputDataSize) {
			this.inputDataSize = inputDataSize;
			return this;
		}

		public Builder setSensorInitialDelay(int sensorInitialDelay) {
			this.sensorInitialDelay = sensorInitialDelay;
			return this;
		}

		public Builder setTrainingSessionsThreshold(Integer trainingSessionsThreshold) {
			this.trainingSessionsThreshold = trainingSessionsThreshold;
			return this;
		}

		public Builder setCleanupThreshold(Integer cleanupThreshold) {
			this.cleanupThreshold = cleanupThreshold;
			return this;
		}

		public LernaAppConfig build() {
			return new LernaAppConfig(this);
		}
	}
}
