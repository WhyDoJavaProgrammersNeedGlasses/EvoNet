import java.lang.reflect.Array;

/**
 * A little class for the most complexer math I will use for this project
 * @author lukas_muenzel
 * @version 1.0.1
 */
public class MathForNeuralNetwork {

    /**
     * A function which returns a number between zero and one (As negative as near to zero and as positive as near to one).
     * Zero and one are exclusive
     * @param input is the x for sigmoid
     * @return the result of the sigmoid function
     */
    public static double sigmoid(double input){
        return Math.pow(Math.E, input) / (1+ Math.pow(Math.E, input));
    }

    /**
     * (currently no use)
     * @param input is the x for the deduction of the sigmoid function
     * @return the result of the sigmoid deduction
     */
    public static double sigmoidDeduction(double input){
        return MathForNeuralNetwork.sigmoid(input) * (1 - MathForNeuralNetwork.sigmoid(input));
    }

    /**
     * Calculates an sum from an input array
     * used by the calculateNetinput() method in the Neuron class and the letCreaturesThink() one in the EvoNet class
     */
    public static double getSumFromArray(double[] input){
        double sum = 0;
        for(int i = 0; i<= input.length - 1; i++){
            sum += input[i];
        }
        return sum;
    }

    /**
     * Returns the index of the max number in a Array (currently no use)
     * @param x the input array
     * @return the index of the highest number in this Array
     */
    public static int max(double[] x){
        double maxNumber = Double.MIN_VALUE;
        int row = 0;
        for(int i = 0; i < x.length; i++){
            if(x[i] > maxNumber){
                maxNumber = x[i];
                row = i;
            }

        }
        return row;
    }
}
