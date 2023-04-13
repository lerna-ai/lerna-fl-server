package ai.lerna.flapi.service

import ai.lerna.flapi.api.dto.DeviceWeights
import ai.lerna.flapi.api.dto.TrainingTask
import ai.lerna.flapi.api.dto.TrainingTaskResponse
import ai.lerna.flapi.api.dto.TrainingWeightsResponse
import ai.lerna.flapi.entity.LernaPrediction
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import org.springframework.data.redis.core.RedisTemplate
import spock.lang.Specification

class StorageServiceTest extends Specification {
	StorageServiceImpl storageService;
	RedisTemplate<String, Object> redisTemplate

	def setup() {
		redisTemplate = Mock(RedisTemplate<String, Object>)
		storageService = new StorageServiceImpl(redisTemplate)
	}

	def "Should active jobs is empty"() {
		when:
			int result = storageService.activeJob.size()
		then:
			result == 0
	}

	def "Should job is deactivated on startup"() {
		given:
			long jobId = 1
		when:
			boolean result = storageService.isJobActive(jobId)
			int size = storageService.activeJob.size()
		then:
			!result
			size == 0
	}

	def "Should job can be deactivated"() {
		given:
			long jobId = 1
			storageService.deactivateJob(jobId)
		when:
			boolean result = storageService.isJobActive(jobId)
			int size = storageService.activeJob.size()
		then:
			!result
			size == 0
	}

	def "Should job can be activated"() {
		given:
			long jobId = 1
			storageService.activateJob(jobId)
		when:
			boolean result = storageService.isJobActive(jobId)
			int size = storageService.activeJob.size()
		then:
			result
			size == 1
	}

	def "Should multiple jobs can be activated"() {
		given:
			long jobId1 = 1
			long jobId2 = 2
			storageService.activateJob(jobId1)
			storageService.activateJob(jobId2)
		when:
			boolean result1 = storageService.isJobActive(jobId1)
			boolean result2 = storageService.isJobActive(jobId2)
			int size = storageService.activeJob.size()
		then:
			result1
			result2
			size == 2
	}

	def "Should multiple jobs can be activated/deactivated"() {
		given:
			long jobId1 = 1
			long jobId2 = 2
			storageService.activateJob(jobId1)
			storageService.deactivateJob(jobId2)
		when:
			boolean result1 = storageService.isJobActive(jobId1)
			boolean result2 = storageService.isJobActive(jobId2)
			int size = storageService.activeJob.size()
		then:
			result1
			!result2
			size == 1
	}

	def "Should get empty active tasks if non task exists"() {
		given:
			String token = "foo-bar-baz"
		when:
			Optional<TrainingTaskResponse> result = storageService.getActiveTask(token)
			int size = storageService.tasks.size()
		then:
			!result.isPresent()
			size == 0
	}

	def "Should can add a training task"() {
		given:
			String token = "foo-bar-baz"
			TrainingTaskResponse task = TrainingTaskResponse.newBuilder().build()
		when:
			storageService.putTask(token, task)
			int size = storageService.tasks.size()
		then:
			size == 1
	}

	def "Should can receive an active training task"() {
		given:
			String token = "foo-bar-baz"
			long version = 5
			TrainingTaskResponse task = TrainingTaskResponse.newBuilder().setVersion(version).build()
		when:
			storageService.putTask(token, task)
			Optional<TrainingTaskResponse> result = storageService.getActiveTask(token)
			int size = storageService.tasks.size()
		then:
			size == 1
			result.isPresent()
			result.get().getVersion() == 5
	}

	def "Should activate job when add a training task and get is as active task"() {
		given:
			String token = "foo-bar-baz"
			long version = 5
			long jobId1 = 1
			long jobId2 = 2
			long jobId3 = 3
			TrainingTaskResponse task = new TrainingTaskResponse(
					version: version,
					trainingTasks: [
							new TrainingTask(jobIds: ["black": jobId1, "brown": jobId2, "blonde": jobId3], mlModel: "hair color")
					])
		when:
			storageService.putTask(token, task)
			Optional<TrainingTaskResponse> result = storageService.getActiveTask(token)
			int size = storageService.tasks.size()
			boolean result1 = storageService.isJobActive(jobId1)
			boolean result2 = storageService.isJobActive(jobId2)
			boolean result3 = storageService.isJobActive(jobId3)
		then:
			size == 1
			result.isPresent()
			with(result.get()) {
				version == 5
				trainingTasks.size() == 1
				trainingTasks.get(0).getMlModel() == "hair color"
			}
			result1
			result2
			result3
	}

	def "Should not return active training task if any of jobs is deactivated"() {
		given:
			String token = "foo-bar-baz"
			long version = 5
			long jobId1 = 1
			long jobId2 = 2
			long jobId3 = 3
			TrainingTaskResponse task = new TrainingTaskResponse(
					version: version,
					trainingTasks: [
							new TrainingTask(jobIds: ["black": jobId1, "brown": jobId2, "blonde": jobId3], mlModel: "hair color")
					])
		when:
			storageService.putTask(token, task)
			storageService.deactivateJob(jobId2)
			Optional<TrainingTaskResponse> result = storageService.getActiveTask(token)
			int size = storageService.tasks.size()
			boolean result1 = storageService.isJobActive(jobId1)
			boolean result2 = storageService.isJobActive(jobId2)
			boolean result3 = storageService.isJobActive(jobId3)
		then:
			size == 1
			!result.isPresent()
			result1
			!result2
			result3
	}

	def "Should get empty tasks if non task exists"() {
		given:
			String token = "foo-bar-baz"
		when:
			Optional<TrainingTaskResponse> result = storageService.getTask(token)
			int size = storageService.tasks.size()
		then:
			!result.isPresent()
			size == 0
	}

	def "Should can receive a training task"() {
		given:
			String token = "foo-bar-baz"
			long version = 5
			TrainingTaskResponse task = TrainingTaskResponse.newBuilder().setVersion(version).build()
		when:
			storageService.putTask(token, task)
			Optional<TrainingTaskResponse> result = storageService.getTask(token)
			int size = storageService.tasks.size()
		then:
			size == 1
			result.isPresent()
			result.get().getVersion() == 5
	}

	def "Should activate job when add a training task and get it"() {
		given:
			String token = "foo-bar-baz"
			long version = 5
			long jobId1 = 1
			long jobId2 = 2
			long jobId3 = 3
			TrainingTaskResponse task = new TrainingTaskResponse(
					version: version,
					trainingTasks: [
							new TrainingTask(jobIds: ["black": jobId1, "brown": jobId2, "blonde": jobId3], mlModel: "hair color")
					])
		when:
			storageService.putTask(token, task)
			Optional<TrainingTaskResponse> result = storageService.getTask(token)
			int size = storageService.tasks.size()
			boolean result1 = storageService.isJobActive(jobId1)
			boolean result2 = storageService.isJobActive(jobId2)
			boolean result3 = storageService.isJobActive(jobId3)
		then:
			size == 1
			result.isPresent()
			with(result.get()) {
				version == 5
				trainingTasks.size() == 1
				trainingTasks.get(0).getMlModel() == "hair color"
			}
			result1
			result2
			result3
	}

	def "Should return training task if any of jobs is deactivated"() {
		given:
			String token = "foo-bar-baz"
			long version = 5
			long jobId1 = 1
			long jobId2 = 2
			long jobId3 = 3
			TrainingTaskResponse task = new TrainingTaskResponse(
					version: version,
					trainingTasks: [
							new TrainingTask(jobIds: ["black": jobId1, "brown": jobId2, "blonde": jobId3], mlModel: "hair color")
					])
		when:
			storageService.putTask(token, task)
			storageService.deactivateJob(jobId2)
			Optional<TrainingTaskResponse> result = storageService.getTask(token)
			int size = storageService.tasks.size()
			boolean result1 = storageService.isJobActive(jobId1)
			boolean result2 = storageService.isJobActive(jobId2)
			boolean result3 = storageService.isJobActive(jobId3)
		then:
			size == 1
			result.isPresent()
			result1
			!result2
			result3
	}

	def "Should initialize jobs after put training task"() {
		given:
			String token = "foo-bar-baz"
			long version = 5
			long jobId1 = 1
			long jobId2 = 2
			long jobIdBoth = 3
			long jobIdA = 4
			long jobIdB = 5
			long deviceId = 1234
			TrainingTaskResponse task = new TrainingTaskResponse(
					version: version,
					trainingTasks: [
							new TrainingTask(jobIds: ["black": jobIdBoth, "brown": jobId1, "blonde": jobId2], mlModel: "hair color")
					])
			TrainingTaskResponse task2 = new TrainingTaskResponse(
					version: version,
					trainingTasks: [
							new TrainingTask(jobIds: ["black": jobIdA, "brown": jobIdB, "blonde": jobIdBoth], mlModel: "hair color")
					])
		when:
			storageService.putTask(token, task)
			storageService.addDeviceWeights(jobIdBoth, deviceId, 123L, Nd4j.zeros(5))
			storageService.deactivateJob(jobId2)
			Optional<TrainingTaskResponse> result = storageService.getTask(token)
			List<DeviceWeights> deviceWeights = storageService.getDeviceWeights(jobIdBoth)
			int size = storageService.tasks.size()
			boolean result1 = storageService.isJobActive(jobId1)
			boolean result2 = storageService.isJobActive(jobId2)
			boolean result3 = storageService.isJobActive(jobIdBoth)
		then:
			size == 1
			result.isPresent()
			deviceWeights.size() == 1
			with(deviceWeights.get(0)) {
				dataPoints == 123
				weights.columns() == 5
			}
			result1
			!result2
			result3
		when:
			storageService.putTask(token, task2)
			List<DeviceWeights> deviceWeights2 = storageService.getDeviceWeights(jobIdBoth)
		then:
			deviceWeights2 == null
	}

	def "Should weights is empty"() {
		when:
			int result = storageService.weights.size()
		then:
			result == 0
	}

	def "Should get empty weights if weights non exists"() {
		given:
			String token = "foo-bar-baz"
		when:
			Optional<TrainingWeightsResponse> result = storageService.getWeights(token)
			int size = storageService.weights.size()
		then:
			!result.isPresent()
			size == 0
	}

	def "Should get empty weights on null weights"() {
		given:
			String token = "foo-bar-baz"
			storageService.putWeights(token, null)
		when:
			Optional<TrainingWeightsResponse> result = storageService.getWeights(token)
			int size = storageService.weights.size()
		then:
			!result.isPresent()
			size == 1
	}

	def "Should weights can be added"() {
		given:
			String token = "foo-bar-baz"
			TrainingWeightsResponse trainingWeightsResponse = new TrainingWeightsResponse()
		when:
			storageService.putWeights(token, trainingWeightsResponse)
			int result = storageService.weights.size()
		then:
			result == 1
	}

	def "Should weights table can be deleted"() {
		given:
			String token = "foo-bar-baz"
			TrainingWeightsResponse trainingWeightsResponse = new TrainingWeightsResponse()
		when:
			storageService.putWeights(token, trainingWeightsResponse)
			int result1 = storageService.weights.size()
			storageService.deleteWeightsTable(token)
			int result2 = storageService.weights.size()
		then:
			result1 == 1
			result2 == 0
	}

	def "Should device can added to dropped list of active jobs"() {
		given:
			String token = "foo-bar-baz"
			long version = 5
			long jobId1 = 1
			long jobId2 = 2
			long jobId3 = 3
			TrainingTaskResponse task = new TrainingTaskResponse(
					version: version,
					trainingTasks: [
							new TrainingTask(jobIds: ["black": jobId1, "brown": jobId2, "blonde": jobId3], mlModel: "hair color")
					])
			long deviceId = 12345L
		when:
			storageService.putTask(token, task)
			storageService.putDeviceIdToDropTable(task.getTrainingTasks(), deviceId)
		then:
			with(storageService.pendingDevices) {
				size() == 3
				keySet().contains(jobId1)
				keySet().contains(jobId2)
				keySet().contains(jobId3)
				with(get(jobId1)) {
					size() == 1
					contains(deviceId)
				}
				with(get(jobId2)) {
					size() == 1
					contains(deviceId)
				}
				with(get(jobId3)) {
					size() == 1
					contains(deviceId)
				}
			}
	}

	def "Should device can added/remove/exists to dropped list of active jobs"() {
		given:
			String token = "foo-bar-baz"
			long version = 5
			long jobId1 = 1
			long jobId2 = 2
			long jobId3 = 3
			TrainingTaskResponse task = new TrainingTaskResponse(
					version: version,
					trainingTasks: [
							new TrainingTask(jobIds: ["black": jobId1, "brown": jobId2, "blonde": jobId3], mlModel: "hair color")
					])
			long deviceId = 12345L
		when:
			storageService.putTask(token, task)
			storageService.putDeviceIdToDropTable(task.getTrainingTasks(), deviceId)
			boolean existsInDropList = storageService.existsDeviceIdOnDropTable(jobId2, deviceId)
			storageService.removeDeviceIdFromDropTable(jobId2, deviceId)
			boolean removedFromDropList = storageService.existsDeviceIdOnDropTable(jobId2, deviceId)
		then:
			existsInDropList
			!removedFromDropList
			with(storageService.pendingDevices) {
				size() == 3
				keySet().contains(jobId1)
				keySet().contains(jobId2)
				keySet().contains(jobId3)
				with(get(jobId1)) {
					size() == 1
					contains(deviceId)
				}
				with(get(jobId2)) {
					size() == 0
					!contains(deviceId)
				}
				with(get(jobId3)) {
					size() == 1
					contains(deviceId)
				}
			}
	}

	def "Should device can remove the dropped list of selected job jobs"() {
		given:
			String token = "foo-bar-baz"
			long version = 5
			long jobId1 = 1
			long jobId2 = 2
			long jobId3 = 3
			TrainingTaskResponse task = new TrainingTaskResponse(
					version: version,
					trainingTasks: [
							new TrainingTask(jobIds: ["black": jobId1, "brown": jobId2, "blonde": jobId3], mlModel: "hair color")
					])
			long deviceId = 12345L
		when:
			storageService.putTask(token, task)
			storageService.putDeviceIdToDropTable(task.getTrainingTasks(), deviceId)
			boolean existsInDropList = storageService.existsDeviceIdOnDropTable(jobId2, deviceId)
			int pendingSize = storageService.getDeviceDropTable(jobId2).size()
			storageService.deleteDropTable(jobId2)
			boolean removedFromDropList = storageService.existsDeviceIdOnDropTable(jobId2, deviceId)
      List<Long> pendingDevices = storageService.getDeviceDropTable(jobId2)
		then:
			existsInDropList
			!removedFromDropList
			with(storageService.pendingDevices) {
				size() == 2
				keySet().contains(jobId1)
				!keySet().contains(jobId2)
				keySet().contains(jobId3)
				with(get(jobId1)) {
					size() == 1
					contains(deviceId)
				}
				with(get(jobId3)) {
					size() == 1
					contains(deviceId)
				}
			}
			pendingSize == 1
			pendingDevices == null
	}

	def "Should job can verify current version of task"() {
		given:
			String token = "foo-bar-baz"
			long version = 5
			long jobId1 = 1
			long jobId2 = 2
			long jobId3 = 3
			TrainingTaskResponse task = new TrainingTaskResponse(
					version: version,
					trainingTasks: [
							new TrainingTask(jobIds: ["black": jobId1, "brown": jobId2, "blonde": jobId3], mlModel: "hair color")
					])
		when:
			storageService.putTask(token, task)
			boolean result1 = storageService.isTaskVersionActive(token, 4)
			boolean result2 = storageService.isTaskVersionActive(token, 5)
			boolean result3 = storageService.isTaskVersionActive(token, 6)
		then:
			!result1
			result2
			!result3
	}

	def "Should service can delete task table"() {
		given:
			String token = "foo-bar-baz"
			long version = 5
			long jobId1 = 1
			long jobId2 = 2
			long jobId3 = 3
			TrainingTaskResponse task = new TrainingTaskResponse(
					version: version,
					trainingTasks: [
							new TrainingTask(jobIds: ["black": jobId1, "brown": jobId2, "blonde": jobId3], mlModel: "hair color")
					])
		when:
			storageService.putTask(token, task)
			boolean result1 = storageService.isTaskVersionActive(token, 5)
			storageService.deleteTaskTable(token)
			boolean result2 = storageService.isTaskVersionActive(token, 5)
		then:
			result1
			!result2
	}

	def "Should service can add device predictions"() {
		given:
			String token = "foo-bar-baz"
			Date timestamp = new Date()
			LernaPrediction prediction = LernaPrediction.newBuilder()
					.setDeviceId(1L)
					.setMLId(2L)
					.setModel("Hair color")
					.setVersion(3)
					.setPrediction("blonde")
					.setTimestamp(timestamp)
					.build()
		when:
			storageService.addDeviceInference(token, prediction)
		then:
			storageService.predictions.size() == 1
			with(storageService.predictions.get(token)) {
				size() == 1
				with(get(0)) {
					getDeviceId() == 1L
					getMLId() == 2L
					getModel() == "Hair color"
					getVersion() == 3
					getPrediction() == "blonde"
					getTimestamp() == timestamp
				}
			}
	}

  def "Should device weights is empty"() {
    when:
      int result = storageService.deviceWeights.size()
    then:
      result == 0
  }

  def "Should device weights not contain job on startup"() {
    given:
      long jobId1 = 1
    when:
      List<DeviceWeights> result = storageService.getDeviceWeights(jobId1)
      int resultSize = storageService.getDeviceWeightsSize(jobId1)
    then:
      result == null
      resultSize == 0
  }

  def "Should device weights can added"() {
    given:
      long jobId1 = 1
      long deviceId = 12345L
      long datapoints = 10000L
      INDArray weights = Nd4j.zeros(100, 1)
    when:
      storageService.addDeviceWeights(jobId1, deviceId, datapoints, weights)
      List<DeviceWeights> result = storageService.getDeviceWeights(jobId1)
      int resultSize = storageService.getDeviceWeightsSize(jobId1)
    then:
      resultSize == 1
      with(result) {
        size() == 1
        with(get(0)) {
          getDataPoints() == datapoints
          getWeights() == weights
        }
      }
  }
}
