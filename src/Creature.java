/**
 * The Creature is the simulated object and will be evoluted and mutated.
 * @author lukas_muenzel
 * @version 1.0.1
 */

import java.awt.*;
import java.io.Serializable;

public class Creature implements Serializable{

    /**
     * If the boolean is true the Creature will be removed from the creatures ArrayList.
     */
    private boolean isDeath = false;

    /**
     * If it's foodValue is under 50 the creature will be death (isDeath = true).
     */
    private double foodValue = 100;

    /**
     * With every SimulationFrame the age gets bigger.
     * I's a multiplicator for all cost (without the getting of children)
     */
    private int age = 1;

    private int numberOfFeelers;
    private Point position = new Point();
    private Net net;

    /**
     * The gen represents the genetic code so the creature can get
     * it's color and calculate the genetic difference to other creatures
     */
    private double[] gen = {Math.random(), Math.random(), Math.random()};

    /**
     * One of the outputs from the last simulationFrame and one of the inputs for the next one.
     * Is for remembering.
     */
    private double[] memory;

    public Creature(int x, int y, int[] numberOfNeurons, int numberOfFeelers, int numberOfMemoryNeurons){
        this.memory = new double[numberOfMemoryNeurons];
        this.position.setLocation(x,y);
        this.net = new Net(numberOfNeurons);
        this.numberOfFeelers = numberOfFeelers;

        for(int i = 0; i < memory.length; i++){
            /*The Double.MIN_VALUE is because the neuron has in the first Frame no meaning*/
            memory[i] = Double.MIN_VALUE;
        }
    }

    public void update(){
        age++;
        if(foodValue <= 50){
            isDeath = true;
        }
    }

    public boolean isDeath() {
        return isDeath;
    }

    public int getAge(){
        return this.age;
    }

    public double getFoodValue() {
        return foodValue;
    }

    public void setFoodValue(double saturation) {
        this.foodValue = saturation;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Net getNet() {
        return net;
    }

    public void setDeath(boolean death) {
        isDeath = death;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setNumberOfFeelers(int numberOfFeelers) {
        this.numberOfFeelers = numberOfFeelers;
    }

    public void setNet(Net net) {
        this.net = net;
    }

    public int getNumberOfFeelers(){
        return this.numberOfFeelers;
    }

    public double[] getGen() {
        return gen;
    }

    public void setGen(double[] gen) {
        this.gen = gen;
    }

    public double[] getMemory() {
        return memory;
    }

    public void setMemory(double[] memory) {
        this.memory = memory;
    }
}
