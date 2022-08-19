package ai.lerna.flapi.config

import ai.lerna.flapi.api.dto.TrainingWeights
import ai.lerna.flapi.api.dto.TrainingWeightsResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.factory.Nd4j
import spock.lang.Specification

class JacksonConfigurationSpec extends Specification {
	ObjectMapper objectMapper

	def setup() {
		objectMapper = JacksonConfiguration.newObjectMapper
	}

	def "writeValueAsString_with_INDArray"() {
		given:
			Map<String, INDArray> weights = new HashMap<String, INDArray>()
			weights.put("test", Nd4j.zeros(1, 5).add(0.5))
			List<TrainingWeights> trainingWeights = new ArrayList<TrainingWeights>()
			trainingWeights.add(new TrainingWeights(mlId: 1, mlName: "Test", accuracy: 0.5, weights: weights))
			TrainingWeightsResponse trainingWeightsResponse = new TrainingWeightsResponse(version: 1, trainingWeights: trainingWeights)
		when:
			String result = objectMapper.writeValueAsString(trainingWeightsResponse)
		then:
			result == "{\"version\":1,\"trainingWeights\":[{\"weights\":{\"test\":{\"array\":\"AApMT05HX1NIQVBFAAAAAAAAAAgABExPTkcAAAAAAAAAAgAAAAAAAAABAAAAAAAAAAUAAAAAAAAA\\r\\nAQAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAABjAApMT05HX1NIQVBFAAAAAAAAAAUABUZM\\r\\nT0FUPwAAAD8AAAA/AAAAPwAAAD8AAAA=\\r\\n\"}},\"mlId\":1,\"mlName\":\"Test\",\"accuracy\":0.5}]}"
	}

	def "readValue_with_INDArray"() {
		given:
			String value = "{\"version\":1,\"trainingWeights\":[{\"weights\":{\"test\":{\"array\":\"AApMT05HX1NIQVBFAAAAAAAAAAgABExPTkcAAAAAAAAAAgAAAAAAAAABAAAAAAAAAAUAAAAAAAAA\\r\\nAQAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAABjAApMT05HX1NIQVBFAAAAAAAAAAUABUZM\\r\\nT0FUPwAAAD8AAAA/AAAAPwAAAD8AAAA=\\r\\n\"}},\"mlId\":1,\"mlName\":\"Test\",\"accuracy\":0.5}]}"
		when:
			TrainingWeightsResponse result = objectMapper.readValue(value, TrainingWeightsResponse.class)
		then:
			with(result) {
				version == 1
				with(trainingWeights) {
					size() == 1
					with(get(0)) {
						mlId == 1
						mlName == "Test"
						accuracy == 0.5
						weights.containsKey("test")
						with(weights.get("test")) {
							rows() == 1
							columns() == 5
							getDouble(0) == 0.5d
							getDouble(1) == 0.5d
							getDouble(2) == 0.5d
							getDouble(3) == 0.5d
							getDouble(4) == 0.5d
						}
					}
				}
			}
	}
}
