package BooneProjekt;

public class PointDataSet {
    private final Point point;
    private final PositionTag positionTag;

    public PointDataSet(Point p, PositionTag positionTag) {
        this.point = p;
        this.positionTag = positionTag;
    }


    /**
     *
     * @return a string containing a visual representation of the dataset (Positioning + Coordinates of Points)
     */
    public String pointsString(){
        return point.toString();
    }

    /**
     *
     * @return returns an array of the point
     */
    public double[] pointToDoubleArray(){
        return new double[] {point.getX(), point.getY()};
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

    public boolean isRest() {
        switch (positionTag) {
            case REST_LEFT, REST_RIGHT, REST_BOTTOM, REST_TOP -> {
                return true;
            }
            default -> { return false; }
        }
    }



    /**
     * all possible positions for points
     */
    public enum PositionTag {
        SIDE_LEFT, SIDE_RIGHT, SIDE_BOTTOM, SIDE_TOP,
        DIAG_FIRST, DIAG_SEC,
        CORNER_TL ,CORNER_TR, CORNER_BL, CORNER_BR,
        REST_LEFT, REST_RIGHT, REST_BOTTOM, REST_TOP
    }
}
