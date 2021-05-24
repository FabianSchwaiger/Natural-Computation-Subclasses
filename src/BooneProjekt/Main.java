package BooneProjekt;

import boone.NetFactory;
import boone.NeuralNet;
import boone.PatternSet;
import boone.Trainer;
import boone.io.BooneFilter;
import boone.training.RpropTrainer;
import boone.util.Conversion;

import java.io.File;



public class Main {
    public static void main(String[] args) throws Exception {
        Dimension dim = new Dimension(0, 1, 0, 1);
        int pointsPerSet = 10;
        int numSets = 2500;

        PointDataSetManager pointDataSetManager = new PointDataSetManager(numSets, pointsPerSet, dim);
        PointDataSetManager pointDataSetManagerTest = new PointDataSetManager(1000, pointsPerSet, dim);
        // pointDataSetManager.printAllDataSets();

        trainNet(pointDataSetManager, pointDataSetManagerTest, pointsPerSet, new BasicPointDataSetValidator());
        //trainNet(pointDataSetManager, pointDataSetManagerTest, pointsPerSet, new BasicHiddenPointDataSetValidator());
        trainNet(pointDataSetManager, pointDataSetManagerTest, pointsPerSet, new AdvancedPointDataSetValidator());
        //trainNet(pointDataSetManager, pointDataSetManagerTest, pointsPerSet, new AdvancedHiddenPointDataSetValidator());

    }

    public static void trainNet(PointDataSetManager pointDataSetManager, PointDataSetManager pointDataSetManagerTest, int pointsPerSet,  PointDataSetValidator validator) throws Exception {
        NeuralNet net = NetFactory.createFeedForward(new int[]{pointsPerSet*2, 17, validator.supportedNumberOfNeurons()}, false, new boone.map.Function.Sigmoid(), new RpropTrainer(), null, null);

        PatternSet patterns = new PatternSet();

        for (PointDataSet dataSet : pointDataSetManager.getAllDataSets()) {
            patterns.getInputs().add(Conversion.asList( dataSet.pointsToDoubleArray1D()));
            patterns.getTargets().add(Conversion.asList(validator.validatePointDataset(dataSet)));
        }


/*
        if( validator instanceof BooneProjekt.BasicPointDataSetValidator) {
            System.out.println("Saving Dataset to file ");
            File datasetFile = new File("basicDataset");
            patterns.save(datasetFile);
        }

        } else {
            System.out.println("Saving Dataset to file ");
            File datasetFile = new File("advDataset");
            patterns.save(datasetFile);
        }
*/


/*
        System.out.println("Loading Dataset from file ");

        if( validator instanceof BasicPointDataSetValidator) {
            patterns = PatternSet.load(new File("basicDataset~"),  new BooneFilter());
        } else {
            patterns = PatternSet.load(new File("advDataset~"),  new BooneFilter());
        }
*/

        PatternSet patternsTest = new PatternSet();
        for (PointDataSet dataSet : pointDataSetManagerTest.getAllDataSets()) {
            patternsTest.getInputs().add(Conversion.asList( dataSet.pointsToDoubleArray1D()));
            patternsTest.getTargets().add(Conversion.asList(validator.validatePointDataset(dataSet)));
        }


        int steps = 5;
        int epochs = 1000;
        Trainer trainer = net.getTrainer();
        trainer.setTrainingData(patterns);
        trainer.setTestData(patterns);
        trainer.setEpochs(epochs);
        trainer.setStepMode(true);											// training in steps
        //System.out.println("*** Training " + (steps * epochs) + " epochs...");

//		trainer.setShuffle(true);
//		((BackpropTrainer)trainer).setMomentum(0.8);
        //System.out.println("Error: ");
        for (int i = 0; i < steps; i++) {
            trainer.train();
            System.out.println((i * epochs));
            System.out.println((i * epochs) + ". - " + net.getTrainer().test());
        }

        //System.out.println("\n*** Testing the network...");

        //System.out.println();

        int ok = 0;
        double sum = 0.0;
        for (int i = 0; i < patternsTest.size(); i++) {
            sum += net.getTrainer().test(patternsTest.getInputs().get(i), patternsTest.getTargets().get(i));
            if(net.getTrainer().test(patternsTest.getInputs().get(i), patternsTest.getTargets().get(i)) < 0.1)
                ok++;
        }

        // System.out.println("Error " + i + " = " + net.getTrainer().test(patternsTest.getInputs().get(i), patternsTest.getTargets().get(i)));
        System.out.println(validator.getClass());
        System.out.println("recognised: " + ok);
        System.out.println("total Error: " + sum);
        // System.out.println("Printing the net:\n" + net);
        //System.out.println("Done.");
    }
}
