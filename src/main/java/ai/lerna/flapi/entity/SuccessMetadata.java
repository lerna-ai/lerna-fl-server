package ai.lerna.flapi.entity;

public class SuccessMetadata {
	private String position;

	private String mlName;

	private boolean isABRandom;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getMlName() {
		return mlName;
	}

	public void setMlName(String mlName) {
		this.mlName = mlName;
	}

	public boolean isABRandom() {
		return isABRandom;
	}

	public void setABRandom(boolean abRandom) {
		isABRandom = abRandom;
	}
}
