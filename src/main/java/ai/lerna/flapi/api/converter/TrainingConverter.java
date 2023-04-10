package ai.lerna.flapi.api.converter;

import ai.lerna.flapi.api.dto.TrainingWeights;
import ai.lerna.flapi.api.dto.TrainingWeightsRequest;
import ai.lerna.flapi.api.dto.TrainingWeightsResponse;
import ai.lerna.flapi.api.dtoV2.TrainingWeightsRequestV2;
import ai.lerna.flapi.api.dtoV2.TrainingWeightsResponseV2;
import ai.lerna.flapi.api.dtoV2.TrainingWeightsV2;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TrainingConverter {

	public final Function<List<Double>, INDArray> convertToINDArrayRow = Nd4j::create;
	public final Function<List<Double>, INDArray> convertToINDArrayColumn = list -> Nd4j.create(list.stream().mapToDouble(Double::doubleValue).toArray(), new int[]{list.size(), 1});

	public final Function<INDArray, List<Double>> convertToArray = indArray -> Arrays.stream(indArray.data().asDouble()).boxed().collect(Collectors.toList());

	public final Function<TrainingWeightsRequestV2, TrainingWeightsRequest> convertTrainingWeightsRequest = trainingWeightsRequestV2 -> TrainingWeightsRequest.newBuilder()
			.setJobId(trainingWeightsRequestV2.getJobId())
			.setDeviceId(trainingWeightsRequestV2.getDeviceId())
			.setVersion(trainingWeightsRequestV2.getVersion())
			.setDatapoints(trainingWeightsRequestV2.getDatapoints())
			.setDeviceWeights(convertToINDArrayColumn.apply(trainingWeightsRequestV2.getDeviceWeights()))
			.build();

	public final Function<TrainingWeightsRequest, TrainingWeightsRequestV2> convertTrainingWeightsRequestV2 = trainingWeightsRequest -> TrainingWeightsRequestV2.newBuilder()
			.setJobId(trainingWeightsRequest.getJobId())
			.setDeviceId(trainingWeightsRequest.getDeviceId())
			.setVersion(trainingWeightsRequest.getVersion())
			.setDatapoints(trainingWeightsRequest.getDatapoints())
			.setDeviceWeights(convertToArray.apply(trainingWeightsRequest.getDeviceWeights()))
			.build();

	public final Function<TrainingWeights, TrainingWeightsV2> convertTrainingWeightsV2 = trainingWeights -> TrainingWeightsV2.newBuilder()
			.setWeights(trainingWeights.getWeights().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> convertToArray.apply(e.getValue()))))
			.setMlId(trainingWeights.getMlId())
			.setMlName(trainingWeights.getMlName())
			.setAccuracy(trainingWeights.getAccuracy())
			.build();

	public final Function<TrainingWeightsResponse, TrainingWeightsResponseV2> convertTrainingWeightsResponseV2 = trainingWeightsResponse -> TrainingWeightsResponseV2.newBuilder()
			.setVersion(trainingWeightsResponse.getVersion())
			.setTrainingWeights(trainingWeightsResponse.getTrainingWeights().stream().map(convertTrainingWeightsV2).collect(Collectors.toList()))
			.build();
}
