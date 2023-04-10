package ai.lerna.flapi.manager

import ai.lerna.flapi.repository.LernaAppRepository
import ai.lerna.flapi.repository.LernaJobRepository
import ai.lerna.flapi.repository.LernaMLRepository
import ai.lerna.flapi.repository.LernaPredictionRepository
import ai.lerna.flapi.repository.MLHistoryDatapointRepository
import ai.lerna.flapi.repository.MLHistoryRepository
import ai.lerna.flapi.repository.SuccessRepository
import ai.lerna.flapi.service.MpcService
import ai.lerna.flapi.service.StorageService
import ai.lerna.flapi.service.WebhookService
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import spock.lang.Specification

class FLManagerImplSpec extends Specification {
	FLManagerImpl flManager;
	MpcService mpcService;
	LernaAppRepository lernaAppRepository;
	LernaMLRepository lernaMLRepository;
	LernaJobRepository lernaJobRepository;
	LernaPredictionRepository lernaPredictionRepository;
	SuccessRepository successRepository;
	MLHistoryRepository mlHistoryRepository;
	MLHistoryDatapointRepository mlHistoryDatapointRepository;
	StorageService storageService;
	WebhookService webhookService;

	def setup() {
		flManager = new FLManagerImpl(mpcService, lernaAppRepository, lernaMLRepository, lernaJobRepository, lernaPredictionRepository, successRepository, mlHistoryRepository, mlHistoryDatapointRepository, storageService, webhookService)
	}

	def "addColumns"() {
		given:
			int initSize = 10
			INDArray array = Nd4j.randn(initSize, 1)
			int noOfColumns = 2
		when:
			INDArray result = flManager.addColumns(array, noOfColumns)
		then:
			array.size(0) + noOfColumns == result.size(0)
			for (int i = 0; i < initSize; i++) {
				array.getDouble(i) == result.getDouble(i)
			}
	}

	def "removeColumns"() {
		given:
			int initSize = 10
			INDArray array = Nd4j.randn(initSize, 1)
			int noOfColumns = 2
		when:
			INDArray result = flManager.removeColumns(array, noOfColumns)
		then:
			array.size(0) - noOfColumns == result.size(0)
			for (int i = 0; i < initSize - noOfColumns; i++) {
				array.getDouble(i) == result.getDouble(i)
			}
	}
}
