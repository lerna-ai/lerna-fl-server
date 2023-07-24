package ai.lerna.flapi.manager;

import java.util.Random;

/**
 * The xavier initializer is a special kind of neural network initializer which optimizes the randomized weights
 * at the beginning for an optimal learning process.
 */
public class XavierInitializer {

	/**
	 * Random object for initialization.
	 */
	private final Random rnd;

	/**
	 * Creates a new xavier initializer with the current system time in milliseconds as seed.
	 */
	public XavierInitializer() {
		this(System.currentTimeMillis());
	}

	/**
	 * Creates a new xavier initializer with the given seed.
	 *
	 * @param seed Seed.
	 */
	public XavierInitializer(long seed) {
		rnd = new Random(seed);
	}

	public double[] initialize(int inputNeurons, int outputNeurons) {
		// Use average neuron count to preserve back propagation signal
		double nAvg = (inputNeurons + outputNeurons) / 2.0;
		double[] weights = new double[inputNeurons * outputNeurons];
		double variance = 1.0 / nAvg;
		double standardDeviation = Math.sqrt(variance);

		// Initialize weights
		for (int i = 0; i < weights.length; i++) {
			weights[i] = xavierWeight(standardDeviation);
		}
		return weights;
	}

	/**
	 * Returns a random xavier weight.
	 *
	 * @param standardDeviation Desired standard deviation.
	 * @return Xavier weight.
	 */
	private double xavierWeight(double standardDeviation) {
		return rnd.nextGaussian() * standardDeviation;
	}

	/**
	 * Returns a random bias value (either -1 or 1).
	 *
	 * @return Random bias.
	 */
	private double randomBias() {
		return rnd.nextBoolean() ? -1.0 : 1.0;
	}
}
