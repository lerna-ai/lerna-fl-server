package ai.lerna.flapi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "ML Application")
public class UserApp implements Serializable {

	@Schema(description = "ID")
	private Long id;

	@Schema(description = "Name")
	private String name;

	public UserApp() {
		// for serialisation/deserialization
	}

	public UserApp(Builder builder) {
		id = builder.id;
		name = builder.name;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(UserApp copy) {
		return newBuilder()
				.setId(copy.getId())
				.setName(copy.getName());
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static final class Builder {
		private Long id;
		private String name;

		public Builder setId(Long id) {
			this.id = id;
			return this;
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public UserApp build() {
			return new UserApp(this);
		}
	}
}
