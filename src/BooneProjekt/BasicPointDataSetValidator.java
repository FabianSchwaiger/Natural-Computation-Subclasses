package BooneProjekt;

public class BasicPointDataSetValidator implements PointDataSetValidator {


    public double[] validatePointDataset(PointDataSet pointDataSet) {
        // BooneProjekt.PointDataSet.PositionTag tag = pointDataSet.getPositionTag();   // Can be used for advanced Subclasses

        /*
        Very Basic Approach
        Net has three Neurons
        First is for Corner
        Second is for Side
        Third is for Diagonal

        Only look at the tag -> some Datasets (especially random ones) might be wrong classified by the validator
         */

        if(pointDataSet.isCorner())
            return new double[] {1, 0, 0};
        else if (pointDataSet.isSide())
            return new double[] {0, 1, 0};
        else if (pointDataSet.isDiag())
            return new double[] {0, 0, 1};
        else
            return new double[] {0, 0, 0};
    }

    @Override
    public int validatePointDatasetTargets(PointDataSet pointDataSet) {
        // PointDataSet.PositionTag tag = pointDataSet.getPositionTag();   // Can be used for advanced Subclasses

        if(pointDataSet.isCorner())
            return 0;
        else if (pointDataSet.isSide())
            return 1;
        else if (pointDataSet.isDiag())
            return 2;
        else
            return -1;
    }


    public int supportedNumberOfNeurons() {
        return 3;
    }
}
