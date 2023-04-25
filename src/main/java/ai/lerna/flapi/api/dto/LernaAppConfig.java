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

	public LernaAppConfig() {
		// for serialisation/deserialization
	}

	public LernaAppConfig(Builder builder) {
		mpcServerUri = builder.mpcServerUri;
		flServerUri = builder.flServerUri;
		uploadPrefix = builder.uploadPrefix;
		logSensorData = builder.logSensorData;
		abTest = builder.abTest;
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
				.setAbTest(copy.getAbTest());
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

	public static final class Builder {
		private String mpcServerUri;
		private String flServerUri;
		private String uploadPrefix;
		private boolean logSensorData;
		private Double abTest;

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

		public LernaAppConfig build() {
			return new LernaAppConfig(this);
		}
	}
}
