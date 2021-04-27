public class PointDataSetManager {
    private PointDataSet[] pointDataSets;
    private int pointsPerSet;
    private Dimension dim;

    public PointDataSetManager(int numSets, int pointsPerSet, Dimension dim) {
        pointDataSets = new PointDataSet[numSets];
        this.pointsPerSet = pointsPerSet;
        this.dim = dim;
        generateDataSets();
    }

    private void generateDataSets() {
        int i = 0;
        for(PointDataSet.PositionTag tag : PointDataSet.PositionTag.values()) {
            do {
                pointDataSets[i] = new PointDataSet(pointsPerSet, dim, tag);
                i++;
            } while(i < pointDataSets.length && ((tag == PointDataSet.PositionTag.RANDOM) || (i % (pointDataSets.length / PointDataSet.PositionTag.values().length) != 0)));
        }
    }

    public int getNumSets() {
        return pointDataSets.length;
    }

    public PointDataSet getDataSet(int index) {
        return pointDataSets[index];
    }

    protected PointDataSet[] getAllDataSets() {
        return pointDataSets;
    }

    public void stringOfAllDataSets() {
        for(PointDataSet dataset: pointDataSets) {
            System.out.println(dataset.pointsString());
        }
    }
}
