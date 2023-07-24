package ai.lerna.flapi.manager;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Arrays;

import static org.nd4j.linalg.indexing.NDArrayIndex.interval;

public class ManagerHelper {
	private final XavierInitializer xavierInitializer = new XavierInitializer();

	INDArray addColumns(INDArray array, int noOfColumns) {
		int targetSize = (int) array.size(0) + noOfColumns;
		double[] intiWeights = xavierInitializer.initialize(targetSize, 1);
		double[] newWeights = Arrays.copyOfRange(intiWeights, intiWeights.length - noOfColumns, intiWeights.length);
		INDArray newColumns = Nd4j.create(newWeights).reshape(noOfColumns, 1);
		return Nd4j.concat(0, array, newColumns);
	}

	INDArray removeColumns(INDArray array, int noOfColumns) {
		return array.get(interval(0, array.size(0) - noOfColumns));
	}
}
