package ai.lerna.flapi.api.dto;

import ai.lerna.flapi.entity.LernaUser;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	@Schema(description = "Profile picture URI")
	private String picture;

	@Schema(description = "Member since")
	private Date createdAt;

	@Schema(description = "ML Apps")
	private List<UserApp> apps;

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
		picture = builder.picture;
		createdAt = builder.createdAt;
		apps = builder.apps;
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
				.setPosition(copy.getPosition())
				.setPicture(copy.getPicture())
				.setCreatedAt(copy.getCreatedAt())
				.setApps(copy.getApps());
	}

	public static Builder newBuilder(LernaUser lernaUser) {
		return newBuilder()
				.setId(lernaUser.getId())
				.setFirstName(lernaUser.getFirstName())
				.setLastName(lernaUser.getLastName())
				.setEmail(lernaUser.getEmail())
				.setCompany(lernaUser.getCompany())
				.setPosition(lernaUser.getPosition())
				.setPicture(lernaUser.getMetadata().getPicture())
				.setCreatedAt(lernaUser.getCreatedAt());
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

	public String getPicture() {
		return picture;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public List<UserApp> getApps() {
		return apps;
	}

	public static final class Builder {
		private long id;
		private String firstName;
		private String lastName;
		private String email;
		private String company;
		private String position;
		private String picture;
		private Date createdAt;
		private List<UserApp> apps;

		private Builder() {
			apps = new ArrayList<>();
		}

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

		public Builder setPicture(String picture) {
			this.picture = picture;
			return this;
		}

		public Builder setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public Builder setApps(List<UserApp> apps) {
			this.apps = apps;
			return this;
		}

		public UserProfile build() {
			return new UserProfile(this);
		}
	}
}
