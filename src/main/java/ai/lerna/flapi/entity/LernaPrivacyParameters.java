package ai.lerna.flapi.entity;

import java.math.BigDecimal;

public class LernaPrivacyParameters {
	private boolean mpc;    //Determines if we are gonna use MPC or not (for now we always use MPC since we do not have implemented local DP)
        private BigDecimal epsilon; //The epsilon value of DP. If eps=0, then no DP noise is added to the final result

	public Boolean getMpc() {
		return mpc;
	}

	public void setMpc(Boolean mpc) {
		this.mpc = mpc;
	}

	public BigDecimal getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(BigDecimal epsilon) {
		this.epsilon = epsilon;
	}
}
