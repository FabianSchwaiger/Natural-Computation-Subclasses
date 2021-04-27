public class Dimension {
    private double minX, maxX, minY, maxY;

    public Dimension(double minX, double maxX, double minY, double maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public Dimension(double maxX,double maxY) {
        this.minX = 0;
        this.maxX = maxX;
        this.minY = 0;
        this.maxY = maxY;
    }

    public double getMinX() {
        return minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxY() {
        return maxY;
    }
}
