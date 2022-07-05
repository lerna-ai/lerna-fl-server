package ai.lerna.flapi.api.dto;

import ai.lerna.flapi.entity.LernaML;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "Lerna Application")
public class LernaApplication {

	@Schema(description = "Application ID")
	private long id;

	@Schema(description = "Application token")
	private String token;

	@Schema(description = "Application name")
	private String name;

	@Schema(description = "Current version")
	private long version;

	@Schema(description = "Number of minimum users")
	private int minNoUsers;

	@Schema(description = "List of MLs")
	private List<LernaML> mls;

	public LernaApplication() {
		// for serialisation/deserialization
	}

	public LernaApplication(Builder builder) {
		id = builder.id;
		mls = builder.mls;
		token = builder.token;
		name = builder.name;
		minNoUsers = builder.minNoUsers;
		version = builder.version;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(LernaApplication copy) {
		return newBuilder()
				.setId(copy.id)
				.setToken(copy.token)
				.setName(copy.name)
				.setVersion(copy.version)
				.setMinNoUsers(copy.minNoUsers)
				.setMls(copy.mls);
	}

	public long getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public String getName() {
		return name;
	}

	public long getVersion() {
		return version;
	}

	public int getMinNoUsers() {
		return minNoUsers;
	}

	public List<LernaML> getMls() {
		return mls;
	}

	public static final class Builder {
		private long id;
		private String token;
		private String name;
		private long version;
		private int minNoUsers;
		private List<LernaML> mls;

		private Builder() {
			mls = new ArrayList<>();
		}

		public Builder setId(long id) {
			this.id = id;
			return this;
		}

		public Builder setToken(String token) {
			this.token = token;
			return this;
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setVersion(long version) {
			this.version = version;
			return this;
		}

		public Builder setMinNoUsers(int minNoUsers) {
			this.minNoUsers = minNoUsers;
			return this;
		}

		public Builder setMls(List<LernaML> mls) {
			this.mls = mls;
			return this;
		}

		public LernaApplication build() {
			return new LernaApplication(this);
		}
	}
}
