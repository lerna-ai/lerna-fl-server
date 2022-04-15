package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Chart Data")
public class WebChartData implements Serializable {

	@Schema(description = "Label")
	private List<String> labels;

	@Schema(description = "Value")
	private List<Double> data;

	public WebChartData() {
		// for serialisation/deserialization
	}

	public WebChartData(Builder builder) {
		labels = builder.labels;
		data = builder.data;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(WebChartData copy) {
		return newBuilder()
				.setData(copy.getData())
				.setLabels(copy.getLabels());
	}

	public List<String> getLabels() {
		return labels;
	}

	public List<Double> getData() {
		return data;
	}

	public static final class Builder {
		private List<String> labels;
		private List<Double> data;

		private Builder() {
			labels = new ArrayList<>();
			data = new ArrayList<>();
		}

		public Builder setLabels(List<String> labels) {
			this.labels = labels;
			return this;
		}

		public Builder setData(List<Double> data) {
			this.data = data;
			return this;
		}

		public WebChartData build() {
			return new WebChartData(this);
		}
	}
}
