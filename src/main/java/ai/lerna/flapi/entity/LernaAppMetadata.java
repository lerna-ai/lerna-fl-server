package ai.lerna.flapi.entity;

import java.util.List;

public class LernaAppMetadata {
	private String uploadPrefix;

	private boolean uploadSensorData;

	private boolean logSensorData;

	private double abTest;

	private int customFeaturesSize;

	private int inputDataSize;

	private int sensorInitialDelay;

	private Integer trainingSessionsThreshold;

	private Integer cleanupThreshold;

	private String actionMLUri;

	private List<String> actionMLIncludeProperties;

	private List<String> actionMLExcludeProperties;

	private boolean actionMLEncryption;

	public String getUploadPrefix() {
		return uploadPrefix;
	}

	public void setUploadPrefix(String uploadPrefix) {
		this.uploadPrefix = uploadPrefix;
	}

	public boolean getUploadSensorData() {
		return uploadSensorData;
	}

	public void setUploadSensorData(boolean uploadSensorData) {
		this.uploadSensorData = uploadSensorData;
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

	public Integer getTrainingSessionsThreshold() {
		return trainingSessionsThreshold;
	}

	public void setTrainingSessionsThreshold(Integer trainingSessionsThreshold) {
		this.trainingSessionsThreshold = trainingSessionsThreshold;
	}

	public Integer getCleanupThreshold() {
		return cleanupThreshold;
	}

	public void setCleanupThreshold(Integer cleanupThreshold) {
		this.cleanupThreshold = cleanupThreshold;
	}

	public String getActionMLUri() {
		return actionMLUri;
	}

	public void setActionMLUri(String actionMLUri) {
		this.actionMLUri = actionMLUri;
	}

	public List<String> getActionMLIncludeProperties() {
		return actionMLIncludeProperties;
	}

	public LernaAppMetadata setActionMLIncludeProperties(List<String> actionMLIncludeProperties) {
		this.actionMLIncludeProperties = actionMLIncludeProperties;
		return this;
	}

	public List<String> getActionMLExcludeProperties() {
		return actionMLExcludeProperties;
	}

	public LernaAppMetadata setActionMLExcludeProperties(List<String> actionMLExcludeProperties) {
		this.actionMLExcludeProperties = actionMLExcludeProperties;
		return this;
	}

	public boolean getActionMLEncryption() {
		return actionMLEncryption;
	}

	public LernaAppMetadata setActionMLEncryption(boolean actionMLEncryption) {
		this.actionMLEncryption = actionMLEncryption;
		return this;
	}
}
