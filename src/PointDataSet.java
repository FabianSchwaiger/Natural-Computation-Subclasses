

public class PointDataSet {
    private Point[] points;
    private final PositionTag positionTag;

    public PointDataSet(int size) {
        this.points = new Point[size];
        this.positionTag = PositionTag.RANDOM;
        generatePoints(new Dimension(0,1,0,1));
    }

    public PointDataSet(int size, Dimension dim) {
        this.points = new Point[size];
        this.positionTag = PositionTag.RANDOM;
        generatePoints(dim);
    }

    public PointDataSet(int size, Dimension dim, PositionTag positionTag) {
        this.points = new Point[size];
        this.positionTag = positionTag;
        generatePoints(dim);
    }

    public PointDataSet(Point[] points) {
        this.positionTag = PositionTag.RANDOM;
        this.points = points;
    }

    public String pointsString(){
        StringBuilder pointsBuilder = new StringBuilder(positionTag.toString() + "\n");

        for(int i = 0; i < points.length; i++) {
            pointsBuilder.append(points[i])
                    .append("\n");
        }

        return pointsBuilder.toString();
    }

    public double[][] pointsToDoubleArray2D(){
        double[][] arr = new double[points.length][2];
        for(int i = 0; i < points.length; i++) {
            arr[i] = points[i].getAsArray();
        }
        return arr;
    }

    public double[] pointsToDoubleArray1D(){
        double[]arr = new double[points.length * 2];
        int i = 0;
        for(Point p : points) {
            arr[i] = p.getX();
            arr[i+1] = p.getY();
            i += 2;
        }
        return arr;
    }

    public PositionTag getPositionTag() {
        return this.positionTag;
    }

    public boolean isCorner() {
        switch (positionTag) {
            case CORNER_BL, CORNER_BR, CORNER_TL, CORNER_TR -> {
                return true;
            }
            default -> { return false; }
        }
    }

    public boolean isSide() {
        switch (positionTag) {
            case SIDE_BOTTOM , SIDE_LEFT , SIDE_RIGHT , SIDE_TOP -> {
                return true;
            }
            default -> { return false; }
        }
    }

    public boolean isDiag() {
        switch (positionTag) {
            case DIAG_FIRST, DIAG_SEC -> {
                return true;
            }
            default -> { return false; }
        }
    }

    public boolean isRandom() {
        return positionTag == PositionTag.RANDOM;
    }


    private void generatePoints(Dimension dim) {
        switch (positionTag) {
            case DIAG_FIRST -> {
                for(int i = 0; i < points.length; i++) {
                    points[i] = Point.generateDiagPoint(dim);
                }
            }
            case DIAG_SEC -> {
                for(int i = 0; i < points.length; i++) {
                    points[i] = Point.generateDiagSecPoint(dim);
                }
            }
            default -> {
                Dimension dim1 = dimensionForPosition(dim);
                for(int i = 0; i < points.length; i++) {
                    points[i] = Point.generateRandomPoint(dim1);
                }
            }
        }
    }

    private Dimension dimensionForPosition(Dimension dim) {
        double minX = dim.getMinX();
        double maxX = dim.getMaxX();
        double minY = dim.getMinY();
        double maxY = dim.getMaxY();

        final double minXNew = Math.abs(minX - maxX) * 0.75 + minX;
        final double maxXNew = (Math.abs(minX + maxX)) * 0.25 + minX;

        final double minYNew = Math.abs(minY - maxY) * 0.75 + minY;
        final double maxYNew = Math.abs(minY - maxY) * 0.25 + minY;

        switch(positionTag) {
            case SIDE_LEFT -> {
                maxX = maxXNew;
            }
            case SIDE_RIGHT -> {
                minX = minXNew;
            }
            case SIDE_TOP -> {
                minY = minYNew;
            }
            case SIDE_BOTTOM -> {
                maxY = maxYNew;
            }
            case CORNER_TL -> {
                minY = minYNew;
                maxX = maxXNew;
            }
            case CORNER_TR -> {
                minY = minYNew;
                minX = minXNew;
            }
            case CORNER_BL -> {
                maxY = maxYNew;
                maxX = maxXNew;
            }
            case CORNER_BR -> {
                maxY = maxYNew;
                minX = minXNew;
            }
        }
        return new Dimension(minX, maxX, minY, maxY);
    }

    public enum PositionTag {
        SIDE_LEFT, SIDE_RIGHT, SIDE_BOTTOM, SIDE_TOP,
        DIAG_FIRST, DIAG_SEC,
        CORNER_TL ,CORNER_TR, CORNER_BL, CORNER_BR,
        RANDOM
    }
}
