package ai.lerna.flapi.entity;

import java.math.BigDecimal;

public class LernaPrivacy {
	private Boolean mpc;

	private BigDecimal eps;

	public Boolean getMpc() {
		return mpc;
	}

	public void setMpc(Boolean mpc) {
		this.mpc = mpc;
	}

	public BigDecimal getEps() {
		return eps;
	}

	public void setEps(BigDecimal eps) {
		this.eps = eps;
	}
}
