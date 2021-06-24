package BooneProjekt;

public class PointDataSetManager {
    private final PointDataSet[] pointDataSet;

    public PointDataSetManager(int numSets) {
        pointDataSet = new PointDataSet[numSets];
        generateDataSets();
    }

    /**
        generates datasets containing all of the 11 positions (corners, sides, diagonals, random)
        splits the positions almost evenly expect for the random position, which is mor common depending on the number of datasets
     */
    private void generateDataSets() {
        int pointsPerSet = (int) Math.ceil((double)pointDataSet.length / PointDataSet.PositionTag.values().length);
        int total = 0;

        for(PointDataSet.PositionTag tag : PointDataSet.PositionTag.values()) {
            for(int i = 0; total < pointDataSet.length && i < pointsPerSet; i++, total++) {
                pointDataSet[total] = new PointDataSet(PointGenerator.generatePoint(tag), tag);
            }
        }
    }

    /**
     *
     * @return returns the total number of datasets
     */
    public int getNumSets() {
        return pointDataSet.length;
    }

    /**
     *
     * @param index
     * @return returns dataset at the given index
     */
    public PointDataSet getDataSet(int index) {
        return pointDataSet[index];
    }

    /**
     * made for simplicity in the main class (while making the pattern set)
     * @return returns all datasets
     */
    protected PointDataSet[] getAllDataSets() {
        return pointDataSet;
    }

    /**
     * prints all Datasets (Position tag + Points)
     */
    public void printAllDataSets() {
        for(PointDataSet p : pointDataSet) {
            System.out.println(p.pointsString());
        }
    }
}
