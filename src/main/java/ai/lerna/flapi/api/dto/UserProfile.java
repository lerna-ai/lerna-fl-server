package ai.lerna.flapi.api.dto;

import ai.lerna.flapi.entity.LernaUser;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "User Profile")
public class UserProfile implements Serializable {

	@Schema(description = "User ID")
	private long id;

	@Schema(description = "First name")
	private String firstName;

	@Schema(description = "Last name")
	private String lastName;

	@Schema(description = "e-mail address")
	private String email;

	@Schema(description = "Company")
	private String company;

	@Schema(description = "Position")
	private String position;

	public UserProfile() {
		// for serialisation/deserialization
	}

	public UserProfile(Builder builder) {
		id = builder.id;
		firstName = builder.firstName;
		lastName = builder.lastName;
		email = builder.email;
		company = builder.company;
		position = builder.position;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(UserProfile copy) {
		return newBuilder()
				.setId(copy.getId())
				.setFirstName(copy.getFirstName())
				.setLastName(copy.getLastName())
				.setEmail(copy.getEmail())
				.setCompany(copy.getCompany())
				.setPosition(copy.getPosition());
	}

	public static Builder newBuilder(LernaUser lernaUser) {
		return newBuilder()
				.setId(lernaUser.getId())
				.setFirstName(lernaUser.getFirstName())
				.setLastName(lernaUser.getLastName())
				.setEmail(lernaUser.getEmail())
				.setCompany(lernaUser.getCompany())
				.setPosition(lernaUser.getPosition());
	}

	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getCompany() {
		return company;
	}

	public String getPosition() {
		return position;
	}

	public static final class Builder {
		private long id;
		private String firstName;
		private String lastName;
		private String email;
		private String company;
		private String position;

		public Builder setId(long id) {
			this.id = id;
			return this;
		}

		public Builder setFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}

		public Builder setCompany(String company) {
			this.company = company;
			return this;
		}

		public Builder setPosition(String position) {
			this.position = position;
			return this;
		}

		public UserProfile build() {
			return new UserProfile(this);
		}
	}
}
