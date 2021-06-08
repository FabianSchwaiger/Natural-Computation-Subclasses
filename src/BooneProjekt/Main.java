package BooneProjekt;

import boone.NetFactory;
import boone.NeuralNet;
import boone.PatternSet;
import boone.Trainer;
import boone.io.BooneFilter;
import boone.training.BackpropTrainer;
import boone.training.RpropTrainer;
import boone.util.Conversion;

import java.io.File;



public class Main {
    static int ok;

    public static void main(String[] args) throws Exception {
        Dimension dim = new Dimension(0, 1, 0, 1);
        int pointsPerSet = 10;
        int numSets = 2500;
        int numTestSets = 500;
        int runs = 1;
        long time = 0;

        PointDataSetManager pointDataSetManager;
        PointDataSetManager pointDataSetManagerTest;
        // pointDataSetManager.printAllDataSets();


            pointDataSetManager = new PointDataSetManager(numSets, pointsPerSet, dim);
            pointDataSetManagerTest = new PointDataSetManager(numTestSets, pointsPerSet, dim);

            time = System.currentTimeMillis();
            //trainNet(pointDataSetManager, pointDataSetManagerTest, pointsPerSet, new BasicPointDataSetValidator());
            //trainNet(pointDataSetManager, pointDataSetManagerTest, pointsPerSet, new BasicHiddenPointDataSetValidator());
            //System.out.println("time needed: " + (System.currentTimeMillis() - time) + "ms");

            //System.out.println();

            time = System.currentTimeMillis();
            //trainNet(pointDataSetManager, pointDataSetManagerTest, pointsPerSet, new AdvancedPointDataSetValidator());
            //trainNet(pointDataSetManager, pointDataSetManagerTest, pointsPerSet, new AdvancedHiddenPointDataSetValidator());
            //System.out.println("time needed: " + (System.currentTimeMillis() - time) + "ms");

        ok = 0;
        for(int i = 0; i < runs; i++) {
            pointDataSetManager = new PointDataSetManager(numSets, pointsPerSet, dim);
            pointDataSetManagerTest = new PointDataSetManager(numTestSets, pointsPerSet, dim);
            trainNet(pointDataSetManager, pointDataSetManagerTest, pointsPerSet, new BasicPointDataSetValidator());
        }
        System.out.println((double)ok / (runs * numTestSets));
        System.out.println();

        ok = 0;
        for(int i = 0; i < runs; i++) {
            pointDataSetManager = new PointDataSetManager(numSets, pointsPerSet, dim);
            pointDataSetManagerTest = new PointDataSetManager(numTestSets, pointsPerSet, dim);
            trainNet(pointDataSetManager, pointDataSetManagerTest, pointsPerSet, new AdvancedPointDataSetValidator());
        }
        System.out.println((double)ok / (runs * numTestSets));
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


        int steps = 1;
        int epochs = 500;
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
            //System.out.println((i * epochs));
            //System.out.println((i * epochs) + ". - " + net.getTrainer().test());
        }

        //System.out.println("\n*** Testing the network...");

        //System.out.println();

        // int ok = 0;
        // double sum = 0.0;
        /*
        for (int i = 0; i < patternsTest.size(); i++) {
            sum += net.getTrainer().test(patternsTest.getInputs().get(i), patternsTest.getTargets().get(i));
            if(net.getTrainer().test(patternsTest.getInputs().get(i), patternsTest.getTargets().get(i)) < 0.1)
                ok++;
            System.out.println(i + ": " + net.getOutputNeuronIndex(net.getTrainer().getWinningNeuron(patternsTest.getInputs().get(i))) + ", " + validator.validatePointDataset(patternsTest.getInputs().get(i)));
        }
*/
        for (PointDataSet dataSet : pointDataSetManagerTest.getAllDataSets()) {
            if(validator.validatePointDatasetTargets(dataSet) != -1) {
                if(net.getOutputNeuronIndex(net.getTrainer().getWinningNeuron(Conversion.asList( dataSet.pointsToDoubleArray1D()))) == validator.validatePointDatasetTargets(dataSet))
                    ok++;
            }
            else
                ok++;
            // System.out.println(net.getOutputNeuronIndex(net.getTrainer().getWinningNeuron(Conversion.asList( dataSet.pointsToDoubleArray1D()))) + ", " + validator.validatePointDatasetTargets(dataSet));
        }

        // System.out.println("Error " + i + " = " + net.getTrainer().test(patternsTest.getInputs().get(i), patternsTest.getTargets().get(i)));
        // System.out.println(validator.getClass().getSimpleName());
        // System.out.println("recognised: " + ok);
        // System.out.println(((double)ok/10) + "%");
        // System.out.println(ok);
        // System.out.println("total Error: " + sum);
        // System.out.println("Printing the net:\n" + net);
        //System.out.println("Done.");
    }
}
