import java.util.*;

/**
 * The Main class which controls all (or most because the buttons in the Simulation class are) classes
 * @author lukas_muenzel
 * @version 1.0.1
 */
public class Main{
    /**
     * The static timer
     */
    static Timer timer = new Timer();

    /**
     * The delay between one and the next tick in milli seconds
     * is edited in the class simulation in the private methods
     * buttonSlowForwardIsClicked() and buttonFastForwardIsClicked()
     */
    static int delayAndPeriod = 1;

    /**
     * The SimulationFrame which is actually in use, mainly edited by the static timer() method in the Main class
     */
    static final SimulationFrame[] actualSimulationFrame = {new SimulationFrame()};

    /**
     * The Simulation which shows the SimulationFrame
     */
    static Simulation simulation = new Simulation();

    /**
     * The EvoNet which can update the new SimulationFrame on base of the old SimulationFrame
     */
    static EvoNet evoNet = new EvoNet();

    /**
     * The main method will run the timer and let
     * the BlockMatrixGeneratorgenerate a new land for the creatures (a new Blockmatrix)
     */
    public static void main(String[] args) {
        actualSimulationFrame[0].setMatrix(BlockMatrixGenerator.generateBlockMatrix());
        timer();


    }

    /**
     * The method for the timer tick
     */
    public static void timer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                /**
                 * Calculate the new SimulationFrame on base of the old one
                 */
                actualSimulationFrame[0] = evoNet.calculateNewSimulationFrame(actualSimulationFrame[0]);

                /**
                 * Show this new SimulationFrame and update the simulationFrame in the Simulation class
                 */
                simulation.showAndUpdateSimulationFrame(actualSimulationFrame[0]);


            }
        }, delayAndPeriod, delayAndPeriod);
    }
}

