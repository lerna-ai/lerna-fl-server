package ai.lerna.flapi.entity;


import ai.lerna.flapi.entity.converter.LernaAppMetadataConverter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "lerna_app")
public class LernaApp {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long id;

	@Column(name = "user_id")
	private long userId;

	@Column(name = "token")
	private String token;

	@Column(name = "current_version")
	private long version;

	@Column(name = "name")
	private String name;

	@Column(name = "no_min_users")
	private int minNoUsers;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "infrastructure_id")
	private Infrastructure infrastructure;

	@Fetch(FetchMode.JOIN)
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_id")
	Set<DeviceBlacklist> deviceBlacklists;

	@Column(name = "metadata")
	@Convert(converter = LernaAppMetadataConverter.class)
	private LernaAppMetadata metadata = new LernaAppMetadata();


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMinNoUsers() {
		return minNoUsers;
	}

	public void setMinNoUsers(int minNoUsers) {
		this.minNoUsers = minNoUsers;
	}

	public Infrastructure getInfrastructure() {
		return infrastructure;
	}

	public void setInfrastructure(Infrastructure infrastructure) {
		this.infrastructure = infrastructure;
	}

	public Set<DeviceBlacklist> getDeviceBlacklists() {
		return deviceBlacklists;
	}

	public void setDeviceBlacklists(Set<DeviceBlacklist> deviceBlacklists) {
		this.deviceBlacklists = deviceBlacklists;
	}

	public LernaAppMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(LernaAppMetadata metadata) {
		this.metadata = metadata;
	}
}
