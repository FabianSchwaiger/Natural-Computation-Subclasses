package BooneProjekt;

public class AdvancedHiddenPointDataSetValidator implements PointDataSetValidator{

    @Override
    public double[] validatePointDataset(PointDataSet pointDataSet) {
        PointDataSet.PositionTag tag = pointDataSet.getPositionTag();   // Can be used for advanced Subclasses

        switch(tag) {
            case PositionTag.CORNER_TL:   return new double[]{1,0,0,0, 0,0,0,0, 0,0};
            case PositionTag.CORNER_TR:   return new double[]{0,1,0,0, 0,0,0,0, 0,0};
            case PositionTag.CORNER_BL:   return new double[]{0,0,1,0, 0,0,0,0, 0,0};
            case PositionTag.CORNER_BR:   return new double[]{0,0,0,1, 0,0,0,0, 0,0};
            case PositionTag.SIDE_LEFT:   return new double[]{0,0,0,0, 1,0,0,0, 0,0};
            case PositionTag.SIDE_RIGHT:  return new double[]{0,0,0,0, 0,1,0,0, 0,0};
            case PositionTag.SIDE_TOP:    return new double[]{0,0,0,0, 0,0,1,0, 0,0};
            case PositionTag.SIDE_BOTTOM: return new double[]{0,0,0,0, 0,0,0,1, 0,0};
            case PositionTag.DIAG_FIRST:  return new double[]{0,0,0,0, 0,0,0,0, 1,0};
            case PositionTag.DIAG_SEC:    return new double[]{0,0,0,0, 0,0,0,0, 0,1};
            case PositionTag.RANDOM:      return new double[]{0,0,0,0, 0,0,0,0, 0,0};
        }
        return null;
    }

    @Override
    public int supportedNumberOfNeurons() {
        return 10;
    }
}

