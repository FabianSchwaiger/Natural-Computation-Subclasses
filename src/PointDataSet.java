

public class PointDataSet {
    private final Point[] points;
    private final Dimension dim;
    private final PositionTag positionTag;

    /**
     * creates a datasets of randomly arranged points in a standard dimension
     * @param size number of points in the dataset
     */
    public PointDataSet(int size) {
        this.points = new Point[size];
        this.positionTag = PositionTag.RANDOM;
        this.dim = new Dimension(0,1,0,1);
        generatePoints();
    }

    /**
     * creates a datasets of randomly arranged points in a specified dimension
     * @param size number of points in the dataset
     * @param dim dimension, in which the points are arranged
     */
    public PointDataSet(int size, Dimension dim) {
        this.points = new Point[size];
        this.positionTag = PositionTag.RANDOM;
        this.dim = dim;
        generatePoints();
    }

    /**
     * creates a datasets of points in a specified dimension arranged in a specific way
     * @param size number of points in the dataset
     * @param dim dimension, in which the points are arranged
     * @param positionTag specifies the arrangement of the points
     */
    public PointDataSet(int size, Dimension dim, PositionTag positionTag) {
        this.points = new Point[size];
        this.positionTag = positionTag;
        this.dim = dim;
        generatePoints();
    }

    /**
     *
     * @return a string containing a visual representation of the dataset (Positioning + Coordinates of Points)
     */
    public String pointsString(){
        StringBuilder pointsBuilder = new StringBuilder(positionTag.toString() + "\n");

        for (Point point : points) {
            pointsBuilder.append(point)
                    .append("\n");
        }

        return pointsBuilder.toString();
    }

    /**
     *
     * @return returns an array of the points (1st dimension are the points, 2nd are the coordinates)
     */
    public double[][] pointsToDoubleArray2D(){
        double[][] arr = new double[points.length][2];
        for(int i = 0; i < points.length; i++) {
            arr[i] = points[i].getAsArray();
        }
        return arr;
    }

    /**
     *
     * @return returns an array of the coordinates of the points (pairs of two describe the position of the point)
     */
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

    /**
     *
     * @return returns the Position Tag (positioning of the points)
     */
    public PositionTag getPositionTag() {
        return this.positionTag;
    }

    /**
     *
     * @return returns true, if the points are arranged in one corner
     */
    public boolean isCorner() {
        switch (positionTag) {
            case CORNER_BL, CORNER_BR, CORNER_TL, CORNER_TR -> {
                return true;
            }
            default -> { return false; }
        }
    }

    /**
     *
     * @return returns true, if the points are arranged on one sine
     */
    public boolean isSide() {
        switch (positionTag) {
            case SIDE_BOTTOM , SIDE_LEFT , SIDE_RIGHT , SIDE_TOP -> {
                return true;
            }
            default -> { return false; }
        }
    }

    /**
     *
     * @return returns true, if the points are arranged in a diagonal
     */
    public boolean isDiag() {
        switch (positionTag) {
            case DIAG_FIRST, DIAG_SEC -> {
                return true;
            }
            default -> { return false; }
        }
    }

    /**
     *
     * @return returns true, if the points are arranged in a random pattern
     */
    public boolean isRandom() {
        return positionTag == PositionTag.RANDOM;
    }

    /**
     * creates all the points in the specified Dimension dependent on the positioning
     * for the diagonals, the specific calculation of the coordinates happens in the Point class
     * otherwise the dimH describes the subspace, where the points can be placed
     */
    private void generatePoints() {
        switch (positionTag) {
            case DIAG_FIRST -> {
                for(int i = 0; i < points.length; i++) {
                    points[i] = Point.generateFirstDiagPoint(dim);
                }
            }
            case DIAG_SEC -> {
                for(int i = 0; i < points.length; i++) {
                    points[i] = Point.generateSecDiagPoint(dim);
                }
            }
            default -> {
                Dimension dimH = dimensionForPosition();
                for(int i = 0; i < points.length; i++) {
                    points[i] = Point.generateRandomPoint(dimH);
                }
            }
        }
    }


    /**
     * shrinks the space (dimension), where the points can be placed according to the position tag
     * the size of this subspace is fixed by the tolerance (currently placed on the bottom side means in the lower 25% of the space)
     * @return returns the valid subspace where points can be placed
     */
    private Dimension dimensionForPosition() {
        double minX = dim.getMinX();
        double maxX = dim.getMaxX();
        double minY = dim.getMinY();
        double maxY = dim.getMaxY();

        final double tolerance = 0.1;

        final double minXNew = Math.abs(minX - maxX) * (1-tolerance) + minX;
        final double maxXNew = (Math.abs(minX + maxX)) * tolerance + minX;

        final double minYNew = Math.abs(minY - maxY) * (1-tolerance) + minY;
        final double maxYNew = Math.abs(minY - maxY) * tolerance + minY;

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

    /**
     * all possible positions for points
     */
    public enum PositionTag {
        SIDE_LEFT, SIDE_RIGHT, SIDE_BOTTOM, SIDE_TOP,
        DIAG_FIRST, DIAG_SEC,
        CORNER_TL ,CORNER_TR, CORNER_BL, CORNER_BR,
        RANDOM
    }
}
