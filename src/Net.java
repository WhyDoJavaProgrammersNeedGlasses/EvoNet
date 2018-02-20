/**
 * The neural net, a propertie of every Creature
 *
 * @author lukas_muenzel
 * @version 1.0.1
 */
public class Net implements java.io.Serializable {

    public int[] numberOfNeurons;

    public boolean hasBias = true;

    /**
     * Read it like weights[fromLayer][fromNeuron][toNeuronInNextLayer]
     */
    public double[][][] weights;

    public Neuron[][] neurons;

    /**
     * @param numberOfNeurons the number of neurons in every layer without the bias
     *                        calls the start() method
     */
    public Net(int[] numberOfNeurons) {

        this.numberOfNeurons = numberOfNeurons;

        neurons = new Neuron[numberOfNeurons.length][];

        for (int i = 0; i < numberOfNeurons.length - 1; i++) {
            neurons[i] = new Neuron[numberOfNeurons[i] + 1];
        }

        neurons[neurons.length - 1] = new Neuron[numberOfNeurons[neurons.length - 1]];
        weights = new double[numberOfNeurons.length - 1][][];

        for (int i = 0; i < weights.length; i++) {
            weights[i] = new double[numberOfNeurons[i] + 1][numberOfNeurons[i + 1]];
        }


        this.start();
    }


    /**
     * @param input the input for every input neuron
     * @return
     */
    public double[] solute(double[] input) {

        if (input.length != numberOfNeurons[0]) {
            System.err.println("input.length has to be equal to the length of the input neurons!");
            return null;
        }

        /**
         * Read it like outputsOfWorkingNeurons[fromLayer][fromNeuron][toNeuronInNextLayer]
         */
        double[][][] outputsOfWorkingNeurons = new double[numberOfNeurons.length - 1][][];

        double[] outputOfOutputNeuron = new double[numberOfNeurons[numberOfNeurons.length - 1]];

        for (int i = 0; i < numberOfNeurons.length - 1; i++) {
            outputsOfWorkingNeurons[i] = new double[numberOfNeurons[i]][numberOfNeurons[i + 1]];
        }

        /**
         * Set the input for the input neurons and let them calculate
         */
        for (int i = 0; i < input.length; i++) {
            neurons[0][i].setInput(input[i]);
            neurons[0][i].calculate();

            /**
             * set the outputs
             */
            for (int nextNeuron = 0; nextNeuron < neurons[1].length - 1; nextNeuron++)
                outputsOfWorkingNeurons[0][i][nextNeuron] = neurons[0][i].getOutputWithoutWeights() * weights[0][i][nextNeuron];
        }

        for (int layer = 1; layer < numberOfNeurons.length - 1; layer++) {
            for (int actualNeuronWithoutBias = 0; actualNeuronWithoutBias < neurons[layer].length - 1; actualNeuronWithoutBias++) {

                /**
                 * Set inputs
                 */
                for (int lastNeuron = 0; lastNeuron < neurons[layer - 1].length - 1; lastNeuron++) {
                    neurons[layer][actualNeuronWithoutBias].setInput(outputsOfWorkingNeurons[layer - 1][lastNeuron][actualNeuronWithoutBias]);
                }

                /**
                 * Let neurons calculate
                 */
                neurons[layer][actualNeuronWithoutBias].calculate();
            }

            /**
             * Set outputs
             */
            for (int actualNeuronWithBias = 0; actualNeuronWithBias < neurons[layer].length - 1; actualNeuronWithBias++) {
                for (int nextNeuron = 0; nextNeuron < neurons[layer + 1].length; nextNeuron++) {
                    outputsOfWorkingNeurons[layer][actualNeuronWithBias][nextNeuron] = neurons[layer][actualNeuronWithBias].getOutputWithoutWeights() * weights[layer][actualNeuronWithBias][nextNeuron];
                }
            }
        }

        /**
         * Make the same thing for the output neurons
         */
        for (int i = 0; i < numberOfNeurons[numberOfNeurons.length - 1] - 1; i++) {
            for (int j = 0; j < numberOfNeurons[numberOfNeurons.length - 2]; j++) {
                neurons[neurons.length - 1][i].setInput(outputsOfWorkingNeurons[outputsOfWorkingNeurons.length - 1][j][i]);
            }
            neurons[neurons.length - 1][i].calculate();
            outputOfOutputNeuron[i] = neurons[neurons.length - 1][i].getOutputWithoutWeights();
        }


        resetAllNeurons();

        return outputOfOutputNeuron;


    }


    /**
     * Resets all neurons, should be done after every solute() and forwardPass(), but cant be before the backwardPass
     */
    public void resetAllNeurons() {

        for (int i = 0; i < neurons.length; i++) {
            for (int j = 0; j < neurons[i].length - 1; j++) {
                neurons[i][j].resetNeuron();
            }
        }
    }


    /**
     * Startings
     */
    public void start() {
        initalizeNeurons();
        fillWeights();

    }

    /**
     * Initialize all Neurons
     * if hasBias is true the last neuron in one layer is a bias
     */
    public void initalizeNeurons() {
        for (int i = 0; i < neurons.length; i++) {
            for (int j = 0; j < neurons[i].length; j++) {
                neurons[i][j] = new Neuron(numberOfNeurons, i);
            }
        }

        for (int i = 0; i < neurons.length - 1; i++) {
            neurons[i][neurons[i].length - 1] = new Bias();
        }
    }


    /**
     * Fill all weights with random doubles between - 0.5 and + 0.5
     */
    public void fillWeights() {
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                for (int k = 0; k < weights[i][j].length; k++) {
                    weights[i][j][k] = Math.random() - 0.5;
                }
            }
        }

    }
}
