package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dashboard information")
public class WebDashboard {

	@Schema(description = "Current Success Prediction")
	private long successPrediction;

	@Schema(description = "Success Prediction Trend")
	private long successPredictionTrend;

	@Schema(description = "Current Total Data")
	private long totalData;

	@Schema(description = "Total Data Trend")
	private long totalDataTrend;

	@Schema(description = "Current Total Devices")
	private long totalDevices;

	@Schema(description = "Total Devices Trend")
	private long totalDevicesTrend;

	@Schema(description = "Learning Iterations")
	private long learningIterations;

	@Schema(description = "Success Prediction Rate")
	private WebChartData successPredictionRate;

	@Schema(description = "Current Device Participating")
	private long deviceParticipating;

	public WebDashboard() {
		// for serialisation/deserialization
	}

	public WebDashboard(Builder builder) {
		successPrediction = builder.successPrediction;
		successPredictionTrend = builder.successPredictionTrend;
		totalData = builder.totalData;
		totalDataTrend = builder.totalDataTrend;
		totalDevices = builder.totalDevices;
		totalDevicesTrend = builder.totalDevicesTrend;
		learningIterations = builder.learningIterations;
		successPredictionRate = builder.successPredictionRate;
		deviceParticipating = builder.deviceParticipating;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(WebDashboard copy) {
		return newBuilder()
				.setSuccessPrediction(copy.successPrediction)
				.setSuccessPredictionTrend(copy.successPredictionTrend)
				.setTotalData(copy.totalData)
				.setTotalDataTrend(copy.totalDataTrend)
				.setTotalDevices(copy.totalDevices)
				.setTotalDevicesTrend(copy.totalDevicesTrend)
				.setLearningIterations(copy.learningIterations)
				.setSuccessPredictionRate(copy.successPredictionRate)
				.setDeviceParticipating(copy.deviceParticipating);
	}

	public long getSuccessPrediction() {
		return successPrediction;
	}

	public long getSuccessPredictionTrend() {
		return successPredictionTrend;
	}

	public long getTotalData() {
		return totalData;
	}

	public long getTotalDataTrend() {
		return totalDataTrend;
	}

	public long getTotalDevices() {
		return totalDevices;
	}

	public long getTotalDevicesTrend() {
		return totalDevicesTrend;
	}

	public long getLearningIterations() {
		return learningIterations;
	}

	public WebChartData getSuccessPredictionRate() {
		return successPredictionRate;
	}

	public long getDeviceParticipating() {
		return deviceParticipating;
	}

	public static final class Builder {
		private long successPrediction;
		private long successPredictionTrend;
		private long totalData;
		private long totalDataTrend;
		private long totalDevices;
		private long totalDevicesTrend;
		private long learningIterations;
		private WebChartData successPredictionRate;
		private long deviceParticipating;

		public Builder setSuccessPrediction(long successPrediction) {
			this.successPrediction = successPrediction;
			return this;
		}

		public Builder setSuccessPredictionTrend(long successPredictionTrend) {
			this.successPredictionTrend = successPredictionTrend;
			return this;
		}

		public Builder setTotalData(long totalData) {
			this.totalData = totalData;
			return this;
		}

		public Builder setTotalDataTrend(long totalDataTrend) {
			this.totalDataTrend = totalDataTrend;
			return this;
		}

		public Builder setTotalDevices(long totalDevices) {
			this.totalDevices = totalDevices;
			return this;
		}

		public Builder setTotalDevicesTrend(long totalDevicesTrend) {
			this.totalDevicesTrend = totalDevicesTrend;
			return this;
		}

		public Builder setLearningIterations(long learningIterations) {
			this.learningIterations = learningIterations;
			return this;
		}

		public Builder setSuccessPredictionRate(WebChartData successPredictionRate) {
			this.successPredictionRate = successPredictionRate;
			return this;
		}

		public Builder setDeviceParticipating(long deviceParticipating) {
			this.deviceParticipating = deviceParticipating;
			return this;
		}

		public WebDashboard build() {
			return new WebDashboard(this);
		}
	}
}
