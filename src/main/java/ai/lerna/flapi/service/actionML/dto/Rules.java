package ai.lerna.flapi.service.actionML.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Rules implements Serializable {
	private String name;
	private List<String> values;
	private Integer bias;

	Rules() {
		// for serialisation/deserialization
	}

	private Rules(Builder builder) {
		name = builder.name;
		values = builder.values;
		bias = builder.bias;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(Rules copy) {
		return newBuilder()
				.setName(copy.getName())
				.setValues(copy.getValues())
				.setBias(copy.getBias());
	}

	public String getName() {
		return name;
	}

	public List<String> getValues() {
		return values;
	}

	public Integer getBias() {
		return bias;
	}

	public static final class Builder {
		private String name;
		private List<String> values;
		private Integer bias;

		private Builder() {
			values = new ArrayList<>();
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setValues(List<String> values) {
			this.values = values;
			return this;
		}

		public Builder setBias(Integer bias) {
			this.bias = bias;
			return this;
		}

		public Rules build() {
			return new Rules(this);
		}
	}
}
