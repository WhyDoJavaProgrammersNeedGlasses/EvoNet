/**
 * @author lukas_muenzel
 * @version 1.0.1
 */
public class Neuron implements java.io.Serializable{
    public int counter = 0;
    public double outputWithoutWeights;

    public int[] numberOfNeurons;
    public int position;

    double[] input;

    /**
     * Those parameters are required to calculate the length of the inputs
     * @param numberOfNeurons the numberOfNeurons[] in the net
     * @param position the position in the layer
     */
    public Neuron(int[] numberOfNeurons, int position){
        this.position = position;
        this.numberOfNeurons = numberOfNeurons;
        this.input = new double[getNumberOfInputs()];
    }

    /**
     * @return the length of the inputs.
     * If the neuron is in the first layer, there will be just one input,
     * else there are going to be as much inputs as neurons in the las layer.
     */
    public int getNumberOfInputs(){
        if(position == 0){
            return 1;
        }
        else{
            return numberOfNeurons[position - 1];
        }
    }

    /**
     * Adds the double to the input[]
     * @param input the input which should be added to the input[]
     */
    public void setInput(double input) {
        this.input[counter] = input;
        counter++;
    }

    /**
     * Calculates the netOutput of the neuron
     */
    public void calculate() {
        setOutputWithoutWeights(MathForNeuralNetwork.sigmoid(MathForNeuralNetwork.getSumFromArray(input)));
    }

    /**
     * Resets the neuron (the counter and the netoutput)
     */
    public void resetNeuron(){
        this.counter = 0;
        this.outputWithoutWeights = 0;
    }

    public void setOutputWithoutWeights(double input){
        this.outputWithoutWeights = input;
    }

    public double getOutputWithoutWeights(){
        return this.outputWithoutWeights;
    }
}
