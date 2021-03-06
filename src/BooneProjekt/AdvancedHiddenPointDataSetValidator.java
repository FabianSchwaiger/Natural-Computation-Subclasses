package BooneProjekt;

public class AdvancedHiddenPointDataSetValidator implements PointDataSetValidator {

    @Override
    public double[] validatePointDataset(PointDataSet pointDataSet) {
        PointDataSet.PositionTag tag = pointDataSet.getPositionTag();

        switch(tag) {
            case CORNER_TL:   return new double[]{1,0,0,0, 0,0,0,0, 0,0, 0,0,0,0};
            case CORNER_TR:   return new double[]{0,1,0,0, 0,0,0,0, 0,0, 0,0,0,0};
            case CORNER_BL:   return new double[]{0,0,1,0, 0,0,0,0, 0,0, 0,0,0,0};
            case CORNER_BR:   return new double[]{0,0,0,1, 0,0,0,0, 0,0, 0,0,0,0};
            case SIDE_LEFT:   return new double[]{0,0,0,0, 1,0,0,0, 0,0, 0,0,0,0};
            case SIDE_RIGHT:  return new double[]{0,0,0,0, 0,1,0,0, 0,0, 0,0,0,0};
            case SIDE_TOP:    return new double[]{0,0,0,0, 0,0,1,0, 0,0, 0,0,0,0};
            case SIDE_BOTTOM: return new double[]{0,0,0,0, 0,0,0,1, 0,0, 0,0,0,0};
            case DIAG_FIRST:  return new double[]{0,0,0,0, 0,0,0,0, 1,0, 0,0,0,0};
            case DIAG_SEC:    return new double[]{0,0,0,0, 0,0,0,0, 0,1, 0,0,0,0};
            case REST_LEFT:   return new double[]{0,0,0,0, 0,0,0,0, 0,0, 1,0,0,0};
            case REST_RIGHT:  return new double[]{0,0,0,0, 0,0,0,0, 0,0, 0,1,0,0};
            case REST_TOP:    return new double[]{0,0,0,0, 0,0,0,0, 0,0, 0,0,1,0};
            case REST_BOTTOM: return new double[]{0,0,0,0, 0,0,0,0, 0,0, 0,0,0,1};
        }
        return null;
    }

    @Override
    public int validatePointDatasetTargets(PointDataSet pointDataSet) {
        PointDataSet.PositionTag tag = pointDataSet.getPositionTag();

        switch(tag) {
            case CORNER_TL:   return 0;
            case CORNER_TR:   return 1;
            case CORNER_BL:   return 2;
            case CORNER_BR:   return 3;
            case SIDE_LEFT:   return 4;
            case SIDE_RIGHT:  return 5;
            case SIDE_TOP:    return 6;
            case SIDE_BOTTOM: return 7;
            case DIAG_FIRST:  return 8;
            case DIAG_SEC:    return 9;
            case REST_LEFT:   return 10;
            case REST_RIGHT:  return 11;
            case REST_TOP:    return 12;
            case REST_BOTTOM: return 13;
        }
        return -1;
    }

    @Override
    public int supportedNumberOfNeurons() {
        return 14;
    }
}

