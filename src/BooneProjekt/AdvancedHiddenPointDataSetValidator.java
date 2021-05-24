package BooneProjekt;

public class AdvancedHiddenPointDataSetValidator implements PointDataSetValidator {

    @Override
    public double[] validatePointDataset(PointDataSet pointDataSet) {
        PointDataSet.PositionTag tag = pointDataSet.getPositionTag();   // Can be used for advanced Subclasses

        switch(tag) {
            case CORNER_TL:   return new double[]{1,0,0,0, 0,0,0,0, 0,0, 0};
            case CORNER_TR:   return new double[]{0,1,0,0, 0,0,0,0, 0,0, 0};
            case CORNER_BL:   return new double[]{0,0,1,0, 0,0,0,0, 0,0, 0};
            case CORNER_BR:   return new double[]{0,0,0,1, 0,0,0,0, 0,0, 0};
            case SIDE_LEFT:   return new double[]{0,0,0,0, 1,0,0,0, 0,0, 0};
            case SIDE_RIGHT:  return new double[]{0,0,0,0, 0,1,0,0, 0,0, 0};
            case SIDE_TOP:    return new double[]{0,0,0,0, 0,0,1,0, 0,0, 0};
            case SIDE_BOTTOM: return new double[]{0,0,0,0, 0,0,0,1, 0,0, 0};
            case DIAG_FIRST:  return new double[]{0,0,0,0, 0,0,0,0, 1,0, 0};
            case DIAG_SEC:    return new double[]{0,0,0,0, 0,0,0,0, 0,1, 0};
            case RANDOM:      return new double[]{0,0,0,0, 0,0,0,0, 0,0, 1};
        }
        return null;
    }

    @Override
    public int supportedNumberOfNeurons() {
        return 11;
    }
}

