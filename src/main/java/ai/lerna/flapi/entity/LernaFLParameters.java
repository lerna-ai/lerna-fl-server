package ai.lerna.flapi.entity;

/**
 * @author gkellaris
 */
public class LernaFLParameters {
	/**
	 * Minimum amount of users to start FL training
	 */
	private int minNoUsers;

	/**
	 * Number of models for 1vAll Logistic Regression
	 */
	private int noJobs;

	public int getMinNoUsers() {
		return minNoUsers;
	}

	public void setMinNoUsers(int minNoUsers) {
		this.minNoUsers = minNoUsers;
	}

	public int getNoJobs() {
		return noJobs;
	}

	public void setNoJobs(int noJobs) {
		this.noJobs = noJobs;
	}
}
