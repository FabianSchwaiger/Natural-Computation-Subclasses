package BooneProjekt;

public interface PointDataSetValidator {
    /**
     *
     * @param pointDataSet dataset to validate
     * @return returns the "correct" target value of the output neurons
     */
    double[] validatePointDataset(PointDataSet pointDataSet);

    /**
     * @return returns the number of supported output neurons
     */
    int supportedNumberOfNeurons();
}
