package ai.lerna.flapi.entity;

public class DeviceBlacklistMetadata {

	private boolean predictionDisabled;

	private boolean mlDisabled;

	private boolean disabled;

	public boolean isPredictionDisabled() {
		return predictionDisabled;
	}

	public void setPredictionDisabled(boolean predictionDisabled) {
		this.predictionDisabled = predictionDisabled;
	}

	public boolean isMlDisabled() {
		return mlDisabled;
	}

	public void setMlDisabled(boolean mlDisabled) {
		this.mlDisabled = mlDisabled;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
