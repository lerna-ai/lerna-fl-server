package ai.lerna.flapi.entity;

public class LernaAppMetadata {
	private String uploadPrefix;

	private boolean logSensorData;

	private double abTest;

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
}
