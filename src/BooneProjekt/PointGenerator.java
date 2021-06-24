package BooneProjekt;

public class PointGenerator {
    public static Point generatePoint(PointDataSet.PositionTag tag) {
        switch(tag) {
            case CORNER_TL:   return generateCornerPoint(true, false);
            case CORNER_TR:   return generateCornerPoint(true, true);
            case CORNER_BL:   return generateCornerPoint(false, false);
            case CORNER_BR:   return generateCornerPoint(false, true);

            case SIDE_LEFT:
            case SIDE_BOTTOM:
            case SIDE_RIGHT:
            case SIDE_TOP:
                return generateSidePoint(tag);

            case DIAG_FIRST:  return generateDiagPoint(true);
            case DIAG_SEC:    return generateDiagPoint(false);
            case REST_LEFT:
            case REST_BOTTOM:
            case REST_RIGHT:
            case REST_TOP:      return generateRestPoint(tag);

        }
        return null;
    }

    private static Point generateCornerPoint(boolean top, boolean right) {
        double x = Math.random() * 0.1;
        double y = Math.random() * 0.1;
        if(right)
            x = 1 - x;

        if(top)
            y = 1 - y;

        return new Point(x,y);
    }

    private static Point generateSidePoint(PointDataSet.PositionTag tag) {
        double c1 = (Math.random() * 0.8) + 0.1;
        double c2 = Math.random() * 0.1;

        switch (tag) {
            case SIDE_LEFT:   return new Point(c2, c1);
            case SIDE_RIGHT:  return new Point(1 - c2, c1);
            case SIDE_TOP:    return new Point(c1, 1 - c2);
            case SIDE_BOTTOM: return new Point(c1, c2);
        }

        return null;
    }

    private static Point generateDiagPoint(boolean first) {
        double x = (Math.random() * 0.8) + 0.1;
        double y = (Math.random() * 0.1) - 0.05; // +- 0.05

        if(first)
            return new Point(x, 1 - (x + y));
        else
            return new Point(x, x + y);
    }


    private static Point generateRestPoint(PointDataSet.PositionTag tag) {
        double c1 = (Math.random() * 0.33);
        double c2 = (Math.random() * 0.7 * (-3 * c1 + 1)) + 0.15;
        c1 += + 0.1;

        switch (tag) {
            case REST_LEFT:   return new Point(c1, c2);
            case REST_RIGHT:  return new Point(1 - c1, c2);
            case REST_TOP:    return new Point(c2, 1 - c1);
            case REST_BOTTOM: return new Point(c2, c1);
        }
        return null;
    }
}
