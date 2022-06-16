package ai.lerna.flapi.manager

import ai.lerna.flapi.api.dto.WebDashboard
import ai.lerna.flapi.entity.LernaApp
import ai.lerna.flapi.repository.InferencesRepository
import ai.lerna.flapi.repository.LernaAppRepository
import ai.lerna.flapi.repository.LernaJobRepository
import ai.lerna.flapi.repository.LernaMLRepository
import ai.lerna.flapi.repository.LernaPredictionRepository
import ai.lerna.flapi.repository.MLHistoryDatapointRepository
import ai.lerna.flapi.repository.MLHistoryRepository
import ai.lerna.flapi.repository.WebhookConfigRepository
import ai.lerna.flapi.service.WebhookService
import spock.lang.Specification

class WebManagerImplSpec extends Specification {
	WebManagerImpl webManagerImpl;
	LernaAppRepository lernaAppRepository;
	LernaMLRepository lernaMLRepository;
	LernaJobRepository lernaJobRepository;
	LernaPredictionRepository lernaPredictionRepository;
	MLHistoryRepository mlHistoryRepository;
	InferencesRepository inferencesRepository;
	MLHistoryDatapointRepository mlHistoryDatapointRepository;
	WebhookConfigRepository webhookConfigRepository;
	WebhookService webhookService;

	def setup() {
		lernaAppRepository = Mock(LernaAppRepository)
		lernaMLRepository = Mock(LernaMLRepository)
		lernaJobRepository = Mock(LernaJobRepository)
		lernaPredictionRepository = Mock(LernaPredictionRepository)
		mlHistoryRepository = Mock(MLHistoryRepository)
		mlHistoryDatapointRepository = Mock(MLHistoryDatapointRepository)
		inferencesRepository = Mock(InferencesRepository)
		webhookConfigRepository = Mock(WebhookConfigRepository)
		webhookService = Mock(WebhookService)
		webManagerImpl = new WebManagerImpl(lernaAppRepository, lernaMLRepository, lernaJobRepository, lernaPredictionRepository, mlHistoryRepository, mlHistoryDatapointRepository, inferencesRepository, webhookConfigRepository, webhookService)
	}

	def "GetDashboardData should return correct data"() {
		given:
			long userId = 1L
			long appId = 2L
		when:
			mlHistoryRepository.getAccuracies(userId, appId) >> [BigDecimal.valueOf(5), BigDecimal.valueOf(10), BigDecimal.valueOf(15)]
			inferencesRepository.getSuccesses(userId, appId, 2) >> BigDecimal.valueOf(20)
			inferencesRepository.getSuccesses(userId, appId, 1) >> BigDecimal.valueOf(25)
			lernaJobRepository.getTotalDataPoints(userId, appId) >> 15000L
			mlHistoryDatapointRepository.getTotalDevicesLastWeek(userId, appId) >> 15L
			lernaPredictionRepository.getTotalDevicesLastWeek(userId, appId) >> 20L
			lernaPredictionRepository.getTotalDevicesPreviousWeek(userId, appId) >> 15L
			lernaAppRepository.getByUserId(userId, appId) >> Optional.of(new LernaApp(version: 5))
			WebDashboard result = webManagerImpl.getDashboardData(userId, appId)
		then:
			with(result) {
				getSuccessPrediction() == 25L
				getSuccessPredictionTrend() == 5
				getTotalData() == 15360000
				getTotalDevices() == 20L
				getTotalDevicesTrend() == 33
				getLearningIterations() == 4
				with(getSuccessPredictionRate()) {
					with(getData()) {
						size() == 3
						get(0) == 5.0
						get(1) == 10.0
						get(2) == 15.0
					}
					with(getLabels()) {
						size() == 3
						get(0) == "1"
						get(1) == "2"
						get(2) == "3"
					}
				}
				getDeviceParticipating() == 15L
			}
	}

	def "GetDashboardData should return correct data even if accuracies contains nulls"() {
		given:
			long userId = 1L
			long appId = 2L
		when:
			mlHistoryRepository.getAccuracies(userId, appId) >> [BigDecimal.valueOf(5), BigDecimal.valueOf(10), BigDecimal.valueOf(15), null]
		inferencesRepository.getSuccesses(userId, appId, 2) >> BigDecimal.valueOf(20)
		inferencesRepository.getSuccesses(userId, appId, 1) >> BigDecimal.valueOf(25)
		lernaJobRepository.getTotalDataPoints(userId, appId) >> 15000L
		mlHistoryDatapointRepository.getTotalDevicesLastWeek(userId, appId) >> 15L
		lernaPredictionRepository.getTotalDevicesLastWeek(userId, appId) >> 20L
		lernaPredictionRepository.getTotalDevicesPreviousWeek(userId, appId) >> 15L
			lernaAppRepository.getByUserId(userId, appId) >> Optional.of(new LernaApp(version: 5))
			WebDashboard result = webManagerImpl.getDashboardData(userId, appId)
		then:
			with(result) {
				getSuccessPrediction() == 25L
				getSuccessPredictionTrend() == 5
				getTotalData() == 15360000
				getTotalDevices() == 20L
				getTotalDevicesTrend() == 33
				getLearningIterations() == 4
				with(getSuccessPredictionRate()) {
					with(getData()) {
						size() == 4
						get(0) == 5.0
						get(1) == 10.0
						get(2) == 15.0
						get(3) == 0.0
					}
					with(getLabels()) {
						size() == 4
						get(0) == "1"
						get(1) == "2"
						get(2) == "3"
						get(3) == "4"
					}
				}
				getDeviceParticipating() == 15L
			}
	}
}
