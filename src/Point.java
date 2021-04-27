public class Point {
    private double x, y;
    private static final double tolerance = 0.1;

    public static Point generateRandomPoint(Dimension dim) {
        double x = ((Math.random() * Math.abs(dim.getMaxX() - dim.getMinX())) + dim.getMinX());
        double y = ((Math.random() * Math.abs(dim.getMaxY() - dim.getMinY())) + dim.getMinY());

        return new Point(x,y);
    }

    public static Point generateRandomPoint(double maxX, double maxY) {
        return generateRandomPoint(new Dimension(0,maxX, 0, maxY));
    }

    public static Point generateDiagPoint(Dimension dim) {
        double x = ((Math.random() * Math.abs(dim.getMaxX() - dim.getMinX())) + dim.getMinX());
        double y = (x / Math.abs(dim.getMaxX() - dim.getMinX())) * Math.abs(dim.getMaxY() - dim.getMinY()); // transpose point on x axis to the y axis

        y *= 1 + ((Math.random() * tolerance) - tolerance/2);    // shift in a small tolerance area
        y = Math.max(dim.getMinY(), Math.min(dim.getMaxY(), y)); // keep in bounds
        return new Point(x,y);
    }

    public static Point generateDiagSecPoint(Dimension dim) {
        double x = ((Math.random() * Math.abs(dim.getMaxX() - dim.getMinX())) + dim.getMinX());
        double y = (1 - (x / Math.abs(dim.getMaxX() - dim.getMinX()))) * Math.abs(dim.getMaxY() - dim.getMinY()); // transpose point on x axis to the y axis

        y *= 1 + ((Math.random() * tolerance) - tolerance/2);    // shift in a small tolerance area
        y = Math.max(dim.getMinY(), Math.min(dim.getMaxY(), y)); // keep in bounds
        return new Point(x,y);
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    protected double getX() {
        return x;
    }

    protected double getY() {
        return y;
    }

    protected double[] getAsArray() {
        return new double[] {x,y};
    }

    public double distance(Point p2) {
        return Math.sqrt(square(this.x- p2.x) + square(this.y) - p2.y);
    }

    public String toString() {
        return x + ", " + y;
    }

    private static double square(double x) {
        return x*x;
    }

}
