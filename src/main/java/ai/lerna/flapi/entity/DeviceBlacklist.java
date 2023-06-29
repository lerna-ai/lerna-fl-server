package ai.lerna.flapi.entity;

import ai.lerna.flapi.entity.converter.DeviceBlacklistMetadataConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "device_blacklist")
public class DeviceBlacklist {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long id;

	@Column(name = "app_id")
	private long appId;

	@Column(name = "device_id")
	private long deviceId;

	@Column(name = "metadata")
	@Convert(converter = DeviceBlacklistMetadataConverter.class)
	private DeviceBlacklistMetadata metadata = new DeviceBlacklistMetadata();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	public DeviceBlacklistMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(DeviceBlacklistMetadata metadata) {
		this.metadata = metadata;
	}
}
