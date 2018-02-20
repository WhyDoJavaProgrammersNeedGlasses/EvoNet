import javafx.scene.Group;
import javafx.stage.Stage;

import java.io.*;


public class Simulation {
    private SimulationFrame actualSimulationFrame = new SimulationFrame();
    private Stage primaryStage;
    private final Group root = new Group();
    private double radiusOfCreatures = 10.0;
    private int delayAndPeriod = 1000;

    private void updateSimulationFrame(SimulationFrame newSimulationFrame) {
        this.actualSimulationFrame = newSimulationFrame;
    }

    private void showSimulationFrame(SimulationFrame simulationFrame) {
        for (int i = 0; i < simulationFrame.getCreatures().size(); i++) {
            if(simulationFrame.getCreatures().get(i).getFoodValue() >= 150){
            /* Print Creature */System.out.println(simulationFrame.getCreatures().get(i).getFoodValue());
            }
        }

        for (int i = 0; i < simulationFrame.getMatrix().length; i++) {
            for (int j = 0; j < simulationFrame.getMatrix()[i].length; j++) {
                /*Print Block*/

            }
        }

    }

    public void showAndUpdateSimulationFrame(SimulationFrame newSimulationFrame) {
        updateSimulationFrame(newSimulationFrame);
        showSimulationFrame(this.actualSimulationFrame);
    }


    public void setRadiusOfCreatures(double newRadius) {
        this.radiusOfCreatures = newRadius;
    }

    //TODO Verbindung UI
    public void buttonSaveIsClicked() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("Saves/" +askUserForFilename()+".ser")
            );
            out.writeObject(this.actualSimulationFrame);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO Verbindung UI
    public void buttonLoadIsClicked() {
        try {

            File f = new File("abc.ser");
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            SimulationFrame sF = (SimulationFrame) ois.readObject();
            ois.close();
            this.actualSimulationFrame = sF;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO Verbindung UI
    private void buttonFastForwardIsClicked() {
        try {
            Main.delayAndPeriod /= 2;
            Main.timer();
        } catch (java.lang.IllegalArgumentException e) {
            //TODO Show Dialog "Max Speed reached"
        }
    }

    //TODO Verbindung UI
    private void buttonSlowForwardIsClicked() {
        try {
            Main.delayAndPeriod *= 2;
            Main.timer();
        } catch (java.lang.IllegalArgumentException e) {
            //TODO Show Dialog "Min Speed reached"
        }
    }

    //TODO
    private String askUserForFilename() {
        return "a";
    }

    //TODO
    private String letUserChooseFile() {
        return "a";
    }

    //TODO
    private int askUserForLongnessOfTimeout() {
        return 1000;
    }

    //TODO Verbindung UI
    private void buttonStopIsClicked() {
         Main.timer.cancel();
     }

     //TODO Verbindung UI
     private void buttonPlayIsClicked() {
             Main.timer();
     }

    public SimulationFrame getActualSimulationFrame() {
        return actualSimulationFrame;
    }
}
