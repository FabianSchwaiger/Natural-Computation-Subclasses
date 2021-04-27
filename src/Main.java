import boone.NetFactory;
import boone.NeuralNet;
import boone.PatternSet;
import boone.Trainer;
import boone.training.RpropTrainer;
import boone.util.Conversion;

public class Main {
    public static void main(String[] args) {
        Dimension dim = new Dimension(0,1, 0, 1);
        int pointsPerSet = 10;
        int numSets = 500;

        /* TODO
            Store Datasets (XML)
            .
            X Create Dataset Base - can be created
            X Create Validator    - is available, but still very basic
            X Create Network      - is available,
        */

        PointDataSetManager pointDataSetManager = new PointDataSetManager(numSets, pointsPerSet, dim);
        // pointDataSetManager.printAllDataSets();

        PointDataSetValidator validator = new BasicPointDataSetValidator();

        // erster Parameter sind Dimensionen des Netzes (#Input, #H1, #H2?, #Output)
        NeuralNet net = NetFactory.createFeedForward(new int[]{pointsPerSet*2, 20, validator.supportedNumberOfNeurons()}, false, new boone.map.Function.Sigmoid(), new RpropTrainer(), null, null);

        PatternSet patterns = new PatternSet();
        for (PointDataSet dataSet : pointDataSetManager.getAllDataSets()) {
            patterns.getInputs().add(Conversion.asList( dataSet.pointsToDoubleArray1D()));
            patterns.getTargets().add(Conversion.asList(validator.validatePointDataset(dataSet)));
        }

        int steps = 10;
        int epochs = 1000;
        Trainer trainer = net.getTrainer();
        trainer.setTrainingData(patterns);
        trainer.setTestData(patterns);
        trainer.setEpochs(epochs);
        trainer.setStepMode(true);											// training in steps
        System.out.println("*** Training " + (steps * epochs) + " epochs...");

//		trainer.setShuffle(true);
//		((BackpropTrainer)trainer).setMomentum(0.8);
        System.out.println("Error: ");
        for (int i = 0; i < steps; i++) {
            trainer.train();
            System.out.println((i * epochs) + ". - " + net.getTrainer().test());
        }

        System.out.println("\n*** Testing the network...");

        System.out.println();
        for (int i = 0; i < patterns.size(); i++)
            System.out.println("Error " + i + " = " + net.getTrainer().test(patterns.getInputs().get(i), patterns.getTargets().get(i)));

        System.out.println("Printing the net:\n" + net);
        System.out.println("Done.");

    }
}
