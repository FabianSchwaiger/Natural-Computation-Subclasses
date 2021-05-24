public class PointDataSetManager {
    private final PointDataSet[] pointDataSets;
    private final int pointsPerSet;
    private final Dimension dim;

    public PointDataSetManager(int numSets, int pointsPerSet, Dimension dim) {
        pointDataSets = new PointDataSet[numSets];
        this.pointsPerSet = pointsPerSet;
        this.dim = dim;
        generateDataSets();
    }

    /**
        generates datasets containing all of the 11 positions (corners, sides, diagonals, random)
        splits the positions almost evenly expect for the random position, which is mor common depending on the number of datasets
     */
    private void generateDataSets() {
        int i = 0;
        for(PointDataSet.PositionTag tag : PointDataSet.PositionTag.values()) {
            do {
                pointDataSets[i] = new PointDataSet(pointsPerSet, dim, tag);
                i++;
                // if the RANDOM PositionTag is reached, every following is Random
                // otherwise split i into even parts (depending on number of datasets and number of position tags
            } while(i < pointDataSets.length && ((tag == PointDataSet.PositionTag.RANDOM) || (i % (pointDataSets.length / PointDataSet.PositionTag.values().length) != 0)));
        }
    }

    /**
     *
     * @return returns the total number of datasets
     */
    public int getNumSets() {
        return pointDataSets.length;
    }

    /**
     *
     * @param index
     * @return returns dataset at the given index
     */
    public PointDataSet getDataSet(int index) {
        return pointDataSets[index];
    }

    /**
     * made for simplicity in the main class (while making the pattern set)
     * @return returns all datasets
     */
    protected PointDataSet[] getAllDataSets() {
        return pointDataSets;
    }

    /**
     * prints all Datasets (Position tag + Points)
     */
    public void printAllDataSets() {
        for(PointDataSet dataset: pointDataSets) {
            System.out.println(dataset.pointsString());
        }
    }
}
