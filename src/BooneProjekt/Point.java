package BooneProjekt;

public class Point {
    private double x, y;
    private static final double tolerance = 0.1;

    /**
     * generates a point randomly in the given dimension
     * @param dim dimension, where the point can be
     * @return returns the generated point
     */
    public static Point generateRandomPoint(Dimension dim) {
        double x = ((Math.random() * Math.abs(dim.getMaxX() - dim.getMinX())) + dim.getMinX());
        double y = ((Math.random() * Math.abs(dim.getMaxY() - dim.getMinY())) + dim.getMinY());

        return new Point(x,y);
    }

    /**
     * generates a point inside the dimension placed closely around the main diagonal (top left to bottom right)
     * @param dim dimension, where the point can be
     * @return returns the generated point
     */
    public static Point generateFirstDiagPoint(Dimension dim) {
        double x = ((Math.random() * Math.abs(dim.getMaxX() - dim.getMinX())) + dim.getMinX());
        double y = (1 - (x / Math.abs(dim.getMaxX() - dim.getMinX()))) * Math.abs(dim.getMaxY() - dim.getMinY()); // transpose point on x axis to the y axis

        y *= 1 + ((Math.random() * tolerance) - tolerance/2);    // shift in a small tolerance area
        y = Math.max(dim.getMinY(), Math.min(dim.getMaxY(), y)); // keep in bounds
        return new Point(x,y);
    }

    /**
     * generates a point inside the dimension placed closely around the secondary diagonal (bottom left to top right)
     * @param dim dimension, where the point can be
     * @return returns the generated point
     */
    public static Point generateSecDiagPoint(Dimension dim) {
        double x = ((Math.random() * Math.abs(dim.getMaxX() - dim.getMinX())) + dim.getMinX());
        double y = (x / Math.abs(dim.getMaxX() - dim.getMinX())) * Math.abs(dim.getMaxY() - dim.getMinY()); // transpose point on x axis to the y axis

        y *= 1 + ((Math.random() * tolerance) - tolerance/2);    // shift in a small tolerance area
        y = Math.max(dim.getMinY(), Math.min(dim.getMaxY(), y)); // keep in bounds
        return new Point(x,y);
    }

    /**
     * creation of points only possible via the given gererate Functions
     * @param x
     * @param y
     */
    private Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    protected double getX() {
        return x;
    }

    protected double getY() {
        return y;
    }

    /**
     *
     * @return returns the coordinates of the point as an array
     */
    protected double[] getAsArray() {
        return new double[] {x,y};
    }

    public String toString() {
        return x + ", " + y;
    }

    /**
     * calculates distance to the given point
     * @param p2
     * @return
     */
    public double distance(Point p2) {
        return Math.sqrt(square(this.x- p2.x) + square(this.y - p2.y));
    }

    /**
     * squares x, used instead of Math.pow(x,2)
     * @param x
     * @return
     */
    private static double square(double x) {
        return x*x;
    }
}
