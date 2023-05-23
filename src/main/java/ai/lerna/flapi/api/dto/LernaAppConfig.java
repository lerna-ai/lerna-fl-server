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

	public LernaAppConfig() {
		// for serialisation/deserialization
	}

	public LernaAppConfig(Builder builder) {
		mpcServerUri = builder.mpcServerUri;
		flServerUri = builder.flServerUri;
		uploadPrefix = builder.uploadPrefix;
		logSensorData = builder.logSensorData;
		abTest = builder.abTest;
		customFeaturesSize = builder.customFeaturesSize;
		inputDataSize = builder.inputDataSize;
		sensorInitialDelay = builder.sensorInitialDelay;
	}

	public static Builder newBuilder() {
		return new LernaAppConfig.Builder();
	}

	public static Builder newBuilder(LernaAppConfig copy) {
		return newBuilder()
				.setMpcServerUri(copy.getMpcServerUri())
				.setFlServerUri(copy.getFlServerUri())
				.setUploadPrefix(copy.getUploadPrefix())
				.setLogSensorData(copy.getLogSensorData())
				.setAbTest(copy.getAbTest())
				.setCustomFeaturesSize(copy.getCustomFeaturesSize())
				.setInputDataSize(copy.getInputDataSize())
				.setSensorInitialDelay(copy.getSensorInitialDelay());
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

	public static final class Builder {
		private String mpcServerUri;
		private String flServerUri;
		private String uploadPrefix;
		private boolean logSensorData;
		private Double abTest;
		private int customFeaturesSize;
		private int inputDataSize;
		private int sensorInitialDelay;

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

		public LernaAppConfig build() {
			return new LernaAppConfig(this);
		}
	}
}
