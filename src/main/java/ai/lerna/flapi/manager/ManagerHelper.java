package ai.lerna.flapi.manager;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import static org.nd4j.linalg.indexing.NDArrayIndex.interval;

public class ManagerHelper {
	INDArray addColumns(INDArray array, int noOfColumns) {
		INDArray newColumns = Nd4j.randn(noOfColumns, 1);
		return Nd4j.concat(0, array, newColumns);
	}

	INDArray removeColumns(INDArray array, int noOfColumns) {
		return array.get(interval(0, array.size(0) - noOfColumns));
	}
}
