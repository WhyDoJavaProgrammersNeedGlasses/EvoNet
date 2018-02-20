/**
 * A Neuron, but it's output without the weights is always one.
 * Is important for the function of neural networks
 * @author lukas_muenzel
 * @version 1.0.1
 */
public class Bias extends Neuron {

    /**
     * The constructor for the bias neuron
     * TODO delete the super() call without destroying the constructor of the Neuron class
     */
    public Bias() {
        super(new int[]{0}, 0);
    }

    /**
     * Overwritten because this method isn't useful for a bias neuron
     *
     * @return null because the bias neuron won't get any inputs
     */
    @Override
    public int getNumberOfInputs() {
        return 0;
    }

    /**
     * Overwritten because this method isn't useful for a bias neuron
     */
    @Override
    public void setInput(double input) {
        /**
         * Warning because this method isn't useful for a bias neuron
         */
        System.err.println("WARNING: DON'T USE THIS METHOD FOR BIAS NEURONS");
    }

    /**
     * Overwritten because this method isn't useful for a bias neuron
     */
    @Override
    public void calculate() {
        /**
         * Warning because this method isn't useful for a bias neuron
         */
        System.err.println("WARNING: DON'T USE THIS METHOD FOR BIAS NEURONS");
    }

    /**
     * Overwritten because this method isn't useful for a bias neuron
     */
    @Override
    public void setOutputWithoutWeights(double input) {
        /**
         * Warning because this method isn't useful for a bias neuron
         */
        System.err.println("WARNING: DON'T USE THIS METHOD FOR BIAS NEURONS");
    }

    /**
     * Overwritten because the output of the bias will be in every case 1
     */
    @Override
    public double getOutputWithoutWeights() {
        return 1;
    }

    /**
     * Overwritten because this method isn't useful for a bias
     * neuron because there is nothing to reset because nothing was ever used
     */
    @Override
    public void resetNeuron() {
        /**
         * Warning because this method isn't useful for a bias neuron
         */
        System.err.println("WARNING: DON'T USE THIS METHOD FOR BIAS NEURONS");
    }
}
