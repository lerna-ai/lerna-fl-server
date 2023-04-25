package ai.lerna.flapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "infrastructure")
public class Infrastructure {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "mpc_uri")
	private String mpcUri;

	@Column(name = "fl_uri")
	private String flUri;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMpcUri() {
		return mpcUri;
	}

	public void setMpcUri(String mpcUri) {
		this.mpcUri = mpcUri;
	}

	public String getFlUri() {
		return flUri;
	}

	public void setFlUri(String flUri) {
		this.flUri = flUri;
	}
}
