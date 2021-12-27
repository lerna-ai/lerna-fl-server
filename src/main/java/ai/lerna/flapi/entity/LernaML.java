package ai.lerna.flapi.entity;

import ai.lerna.flapi.entity.converter.LernaMLConverter;
import ai.lerna.flapi.entity.converter.LernaPrivacyConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "lerna_ml")
public class LernaML {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long id;

	@Column(name = "app_id")
	private long appId;

	@Column(name = "model")
	private String model;

	@Column(name = "privacy_parameters")
	@Convert(converter = LernaPrivacyConverter.class)
	private LernaPrivacyParameters privacy = new LernaPrivacyParameters();

	@Column(name = "ml_parameters")
	@Convert(converter = LernaMLConverter.class)
	private LernaMLParameters ml = new LernaMLParameters();

	@Column(name = "accuracy")
	private BigDecimal accuracy;

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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public LernaPrivacyParameters getPrivacy() {
		return privacy;
	}

	public void setPrivacy(LernaPrivacyParameters privacy) {
		this.privacy = privacy;
	}

	public LernaMLParameters getML() {
		return ml;
	}

	public void setPrivacy(LernaMLParameters ml) {
		this.ml = ml;
	}

	public BigDecimal getAccuracy() {
		return accuracy;
	}

	public void setEps(BigDecimal accuracy) {
		this.accuracy = accuracy;
	}
}
