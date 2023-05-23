package ai.lerna.flapi.entity;

public class LernaAppMetadata {
	private String uploadPrefix;

	private boolean logSensorData;

	private double abTest;

	private int customFeaturesSize;

	private int inputDataSize;

	private int sensorInitialDelay;

	public String getUploadPrefix() {
		return uploadPrefix;
	}

	public void setUploadPrefix(String uploadPrefix) {
		this.uploadPrefix = uploadPrefix;
	}

	public boolean getLogSensorData() {
		return logSensorData;
	}

	public void setLogSensorData(boolean logSensorData) {
		this.logSensorData = logSensorData;
	}

	public double getAbTest() {
		return abTest;
	}

	public void setAbTest(double abTest) {
		this.abTest = abTest;
	}

	public int getCustomFeaturesSize() {
		return customFeaturesSize;
	}

	public void setCustomFeaturesSize(int customFeaturesSize) {
		this.customFeaturesSize = customFeaturesSize;
	}

	public int getInputDataSize() {
		return inputDataSize;
	}

	public void setInputDataSize(int inputDataSize) {
		this.inputDataSize = inputDataSize;
	}

	public int getSensorInitialDelay() {
		return sensorInitialDelay;
	}

	public void setSensorInitialDelay(int sensorInitialDelay) {
		this.sensorInitialDelay = sensorInitialDelay;
	}
}
