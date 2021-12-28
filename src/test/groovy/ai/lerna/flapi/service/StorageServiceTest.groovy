package ai.lerna.flapi.service

import ai.lerna.flapi.api.dto.TrainingTask
import ai.lerna.flapi.api.dto.TrainingTaskResponse
import ai.lerna.flapi.api.dto.TrainingWeightsResponse
import spock.lang.Specification

class StorageServiceTest extends Specification {
  StorageServiceImpl storageService;

  def setup() {
    storageService = new StorageServiceImpl()
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

  def "Should activate job when add a training task"() {
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

  def "Should not return training task if any of jobs is deactivated"() {
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
      !result.isPresent()
      result1
      !result2
      result3
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
}
