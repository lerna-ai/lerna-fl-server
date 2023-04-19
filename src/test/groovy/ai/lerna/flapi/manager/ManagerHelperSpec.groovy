package ai.lerna.flapi.manager

import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import spock.lang.Specification

class ManagerHelperSpec extends Specification {
	ManagerHelper managerHelper;

	def setup() {
		managerHelper = new ManagerHelper()
	}

	def "addColumns"() {
		given:
			int initSize = 10
			INDArray array = Nd4j.randn(initSize, 1)
			int noOfColumns = 2
		when:
			INDArray result = managerHelper.addColumns(array, noOfColumns)
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
			INDArray result = managerHelper.removeColumns(array, noOfColumns)
		then:
			array.size(0) - noOfColumns == result.size(0)
			for (int i = 0; i < initSize - noOfColumns; i++) {
				array.getDouble(i) == result.getDouble(i)
			}
	}
}
