package ai.lerna.flapi.validation;

import ai.lerna.flapi.api.dto.RecommendationEngineRequest;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class RecommendationApiValidator {
	List<String> acceptedTTL = Arrays.asList("1 days", "7 days", "14 days", "30 days", "180 days", "360 days");
	List<String> acceptedRankingTypes = Arrays.asList("popular", "trending", "hot", "userDefined", "random");

	public void validate(String engineId) throws ValidationException {
		if (engineId == null || engineId.isEmpty()) {
			throw new ValidationException("Field engineId is required");
		}
		if (!engineId.matches("^[a-z]+(-[a-z]+)*$")) {
			throw new ValidationException("Field engineId should be contains only lowercase characters and hyphen. e.g. test-engine");
		}
	}

	public void validate(RecommendationEngineRequest engine) throws ValidationException {
		if (engine == null) {
			throw new ValidationException("Engine request should not be null");
		}

		validate(engine.getEngineId());

		if (engine.getDatasetTtl() == null || !acceptedTTL.contains(engine.getDatasetTtl())) {
			throw new ValidationException("Field datasetTtl should be contains valid value. e.g. " + String.join(",", acceptedTTL));
		}

		if (engine.getAlgorithmIndicators() == null || engine.getAlgorithmIndicators().isEmpty()) {
			throw new ValidationException("Field algorithmIndicators should not be empty");
		}

		if (engine.getAlgorithmRankingType() == null || !acceptedRankingTypes.contains(engine.getAlgorithmRankingType())) {
			throw new ValidationException("Field algorithmRankingType should be contains valid value. e.g. " + String.join(",", acceptedRankingTypes));
		}

		if ("popular".equals(engine.getAlgorithmRankingType())) {
			if (engine.getAlgorithmRankingIndicatorNames() == null || engine.getAlgorithmRankingIndicatorNames().isEmpty()) {
				throw new ValidationException("Field algorithmRankingIndicatorNames should not be empty on popular ranking.");
			}
			if (engine.getAlgorithmRankingIndicatorNames().stream()
					.anyMatch(rankingIndicator -> !engine.getAlgorithmIndicators().contains(rankingIndicator))) {
				throw new ValidationException("Each item of algorithmRankingIndicatorNames should be contained on algorithmIndicators list.");
			}
		}
		else {
			if (engine.getAlgorithmRankingIndicatorNames() != null && !engine.getAlgorithmRankingIndicatorNames().isEmpty()) {
				throw new ValidationException("Field algorithmRankingIndicatorNames should be empty on non popular ranking.");
			}
		}

		if (engine.getAlgorithmRankingDuration() != null && !acceptedTTL.contains(engine.getAlgorithmRankingDuration())) {
			throw new ValidationException("Field algorithmRankingDuration should be contains valid value. e.g. " + String.join(",", acceptedTTL));
		}
	}
}
