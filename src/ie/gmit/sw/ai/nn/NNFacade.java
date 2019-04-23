package ie.gmit.sw.ai.nn;

/**
 * NNFacade - Controls the Neural Network of the spider
 */
import ie.gmit.sw.ai.nn.activator.*;

public class NNFacade implements NNPerformable {

	//The input data that will be used to train the NN
    private double[][] data = { //Health, Rage, Strength, Defense
          { 2, 2, 2, 0 }, { 1, 2, 2, 0 }, { 0, 2, 2, 0 }, // red
          { 2, 0, 0, 2 }, { 1, 0, 0, 2 }, { 0, 0, 0, 2 }, // green
          { 2, 2, 1, 0 }, { 1, 2, 1, 0 }, { 0, 2, 1, 0 }, // orange
          { 2, 2, 0, 2 }, { 1, 2, 0, 2 }, { 0, 2, 0, 2 }, // Black
          { 2, 1, 1, 1 }, { 1, 1, 1, 1 }, { 0, 1, 1, 1 }, // Brown
          { 2, 0, 1, 1 }, { 1, 0, 1, 1 }, { 0, 0, 1, 1 }, // Yellow
          { 2, 1, 0, 2 }, { 1, 1, 0, 2 }, { 0, 1, 0, 2 }, // Blue
          { 2, 0, 2, 0 }, { 1, 0, 2, 0 }, { 0, 0, 2, 0 }  // Grey
    };
    
    //Expected Results based on inputs
    private double[][] expected = { //Walk, Attack, Run
          {0.0, 1.0, 0.0}, {0.0, 1.0, 0.0}, {0.0, 1.0, 0.0},
          {1.0, 0.0, 0.0}, {1.0, 0.0, 0.0}, {0.0, 0.0, 1.0},
          {0.0, 1.0, 0.0}, {0.0, 1.0, 0.0}, {1.0, 0.0, 0.0},
          {0.0, 1.0, 0.0}, {0.0, 0.0, 1.0}, {0.0, 0.0, 1.0},
          {0.0, 1.0, 0.0}, {0.0, 0.0, 1.0}, {1.0, 0.0, 0.0},
          {1.0, 0.0, 0.0}, {1.0, 0.0, 0.0}, {0.0, 0.0, 1.0},
          {0.0, 1.0, 0.0}, {0.0, 0.0, 1.0}, {1.0, 0.0, 0.0},
          {0.0, 0.0, 1.0}, {1.0, 0.0, 0.0}, {0.0, 0.0, 0.1}
    };

    public int action(double health, double rage, double strength, double defence) throws Exception{

    	//Parameters passed in from the Spider
        double[] params = {health, rage, strength, defence};

        //Initialize Neural Network with a Sigmoidal Function
        NeuralNetwork nn = new NeuralNetwork(Activator.ActivationFunction.Sigmoid, 4, 3, 3);
        //Initialize Trainer with Backpropagation passing in the NN
        Trainator trainer = new BackpropagationTrainer(nn);
        //Train the NN based on data, desired output, alpha and epoch count
        trainer.train(data, expected, 0.5, 100000);

        //Process results with Spiders input data into the trained model
        double[] result = nn.process(params);

        System.out.println("========Results========");
        for (double val : result){
            System.out.println(val);
        }

        System.out.println("=======================\nOutput Result: " + (Utils.getMaxIndex(result) + 1) + "\n");

        int output = (Utils.getMaxIndex(result) + 1);

        return output;
    }
}