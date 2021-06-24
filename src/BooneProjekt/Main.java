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
import java.io.IOException;


public class Main {
    static int ok;
    static int hidden = 0;
    public static void main(String[] args) throws Exception {

        int numSets = 2500;
        int numTestSets = 500;
        int runs = 10;
        long time = 0;

        PointDataSetManager pointDataSetManager;
        PointDataSetManager pointDataSetManagerTest;
        PointDataSetValidator validator;


        //pointDataSetManager = generateAndSave(new BasicHiddenPointDataSetValidator(), new File("Training"), numSets);
        //pointDataSetManagerTest = generateAndSave(new BasicHiddenPointDataSetValidator(), new File("TestTraining"), numTestSets);

        // pointDataSetManager = new PointDataSetManager(numSets);

        /*
        ok = 0;
        pointDataSetManager = generateAndSave(true, new BasicHiddenPointDataSetValidator(), new File("HiddenTraining"), numSets);
        pointDataSetManagerTest = generateAndSave(true, new BasicHiddenPointDataSetValidator(), new File("HiddenTest"), numTestSets);
        trainNet(pointDataSetManager, pointDataSetManagerTest, new AdvancedPointDataSetValidator());

        ok = 0;
        pointDataSetManager = generateAndSave(false, new BasicPointDataSetValidator(), new File("NoHiddenTraining"), numSets);
        pointDataSetManagerTest = generateAndSave(false, new BasicPointDataSetValidator(), new File("NoHiddenTest"), numTestSets);
        trainNet(pointDataSetManager, pointDataSetManagerTest, new AdvancedPointDataSetValidator());
        */

        // pointDataSetManager.printAllDataSets();


            //pointDataSetManager = new PointDataSetManager(numSets, pointsPerSet, dim, true);
            //pointDataSetManagerTest = new PointDataSetManager(numTestSets, pointsPerSet, dim, true);

            //time = System.currentTimeMillis();
            //trainNet(pointDataSetManager, pointDataSetManagerTest, pointsPerSet, new BasicPointDataSetValidator());
            //trainNet(pointDataSetManager, pointDataSetManagerTest, pointsPerSet, new BasicHiddenPointDataSetValidator());
            //System.out.println("time needed: " + (System.currentTimeMillis() - time) + "ms");

            //System.out.println();

            //time = System.currentTimeMillis();
            //trainNet(pointDataSetManager, pointDataSetManagerTest, pointsPerSet, new AdvancedPointDataSetValidator());
            //trainNet(pointDataSetManager, pointDataSetManagerTest, pointsPerSet, new AdvancedHiddenPointDataSetValidator());
            //System.out.println("time needed: " + (System.currentTimeMillis() - time) + "ms");


        // TESTS
        for(hidden = 0; hidden <= 17; hidden++) {
            System.out.println(hidden);
            time = System.currentTimeMillis();
            ok = 0;
            for (int i = 0; i < runs; i++) {
                pointDataSetManager = new PointDataSetManager(numSets);
                pointDataSetManagerTest = new PointDataSetManager(numTestSets);
                trainNet(pointDataSetManager, pointDataSetManagerTest, new BasicHiddenPointDataSetValidator());
            }
            System.out.println((double) ok / (runs * numTestSets));
            //System.out.println();

            ok = 0;
            for (int i = 0; i < runs; i++) {
                pointDataSetManager = new PointDataSetManager(numSets);
                pointDataSetManagerTest = new PointDataSetManager(numTestSets);
                trainNet(pointDataSetManager, pointDataSetManagerTest, new AdvancedHiddenPointDataSetValidator());
            }
            System.out.println((double) ok / (runs * numTestSets));
            System.out.println(System.currentTimeMillis() - time);
            System.out.println();
        }
    }

    public static PointDataSetManager generateAndSave(PointDataSetValidator validator, File f, int numSets) throws IOException {
        PointDataSetManager pointDataSetManager = new PointDataSetManager(numSets);

        PatternSet patterns = new PatternSet();

        for (PointDataSet dataSet : pointDataSetManager.getAllDataSets()) {
            patterns.getInputs().add(Conversion.asList( dataSet.pointToDoubleArray()));
            patterns.getTargets().add(Conversion.asList(validator.validatePointDataset(dataSet)));
        }

        patterns.save(f);

        return pointDataSetManager;
    }

    public static void trainNet(PointDataSetManager pointDataSetManager, PointDataSetManager pointDataSetManagerTest,  PointDataSetValidator validator) throws Exception {
        NeuralNet net;
        if(hidden != 0)
            net = NetFactory.createFeedForward(new int[]{2, hidden, validator.supportedNumberOfNeurons()}, false, new boone.map.Function.Sigmoid(), new RpropTrainer(), null, null);
        else
            net = NetFactory.createFeedForward(new int[]{2, validator.supportedNumberOfNeurons()}, false, new boone.map.Function.Sigmoid(), new RpropTrainer(), null, null);

        PatternSet patterns = new PatternSet();

        for (PointDataSet dataSet : pointDataSetManager.getAllDataSets()) {
            patterns.getInputs().add(Conversion.asList( dataSet.pointToDoubleArray()));
            patterns.getTargets().add(Conversion.asList(validator.validatePointDataset(dataSet)));
        }

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
            patternsTest.getInputs().add(Conversion.asList( dataSet.pointToDoubleArray()));
            patternsTest.getTargets().add(Conversion.asList(validator.validatePointDataset(dataSet)));
        }


        int steps = 2;
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
            if(net.getOutputNeuronIndex(net.getTrainer().getWinningNeuron(Conversion.asList( dataSet.pointToDoubleArray()))) == validator.validatePointDatasetTargets(dataSet))
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
