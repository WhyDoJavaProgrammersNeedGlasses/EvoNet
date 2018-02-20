/**
 * The class which calculates a new SimulationFrame on base of the old one.
 * The heart of the project.
 * @author lukas_muenzel
 * @version 1.0.1
 * TODO Feelers/Mating
 */

import java.awt.*;
import java.util.ArrayList;

public class EvoNet {
    /**
     * Calculates a new SimulationFrame on base of a old one
     * @param simulationFrame the old SimulationFrame
     * @return the new SimulationFrame
     */
    public SimulationFrame calculateNewSimulationFrame(SimulationFrame simulationFrame) {
        /*Update all the blocks in the blockMatrix (humit)*/
        simulationFrame = updateBlocks(simulationFrame);

        /*Update all the creatures in the creatures ArrayList (thinking, aging, testing, deleting)*/
        simulationFrame = updateCreatures(simulationFrame);

        return simulationFrame;
    }

    /**
     * Updates (Humits) all earth blocks
     *
     * @param sF the old SimulationFrame without of humited blocks
     * @return the new SimulationFrame with humited earth blocks
     */
    private SimulationFrame updateBlocks(SimulationFrame sF) {
        Block[][] matrix = sF.getMatrix();

        /*Go though every block in the blockMatrix*/
        for (int i = 0; i < sF.getMatrix().length; i++) {
            for (int j = 0; j < sF.getMatrix().length; j++) {
                if (matrix[i][j].getClass() == Earth.class)
                    if (matrix[i][j].getFoodValue() < 50) {
                        /*If the block is earth and his foodValue is under 50
                        * add one to the foodValue (humit the block)*/
                        matrix[i][j].setFoodValue(matrix[i][j].getFoodValue() + 1);
                    }
            }
        }

        sF.setMatrix(matrix);
        return sF;
    }

    /**
     * Updates all creatures in the creatures ArrayList (thinking, aging, testing, deleting)
     *
     * @param sF the old SimulationFrame without of updated creatures
     * @return the new SimulationFrame with updated creatures
     */
    private SimulationFrame updateCreatures(SimulationFrame sF) {

        sF = letCreaturesThink(sF);
        sF = runUpdateOnCreatures(sF);
        sF = deleteDeathCreatures(sF);
        ;
        sF = testNumberOfCreatures(sF, 30);

        return sF;
    }


    /**
     * Let all creatures calculate their outputs on base of the inputs and
     *
     * @param sF the old SimulationFrame without of updated creatures
     * @return the new SimulationFrame with updated creatures
     */
    private SimulationFrame letCreaturesThink(SimulationFrame sF) {
        ArrayList<Creature> creatures = sF.getCreatures();
        Block[][] matrix = sF.getMatrix();

        for (int i = 0; i < sF.getCreatures().size(); i++) {

            double[] think = creatures.get(i).getNet().solute(generateInputForOneCreature(sF, i));

            /*Walk right*/
            if (creatures.get(i).getPosition().x + (int) (think[0] * 100) <= 50)
                creatures.get(i).setPosition(
                        new Point(creatures.get(i).getPosition().x + (int) (think[0] * 10), creatures.get(i).getPosition().y));

            /*Walk left*/
            if (creatures.get(i).getPosition().x - (int) (think[1] * 100) >= 0)
                creatures.get(i).setPosition(
                        new Point(creatures.get(i).getPosition().x - (int) (think[1] * 10), creatures.get(i).getPosition().y));

            /*Walk down*/
            if (creatures.get(i).getPosition().y + (int) (think[2] * 100) <= 50)
                creatures.get(i).setPosition(
                        new Point(creatures.get(i).getPosition().x, creatures.get(i).getPosition().y + (int) (think[2] * 100)));

            /*Walk up*/
            if (creatures.get(i).getPosition().y - (int) (think[3] * 100) >= 0)
                creatures.get(i).setPosition(
                        new Point(creatures.get(i).getPosition().x, creatures.get(i).getPosition().y - (int) (think[3] * 100)));

            /*Eat*/
            sF = letOneCreatureEat(sF, i, think[4]);

            /*Pair*/
            sF = letOneCreaturePair(sF, i, think[5]);

            /*Set memory neurons*/
            for (int j = 0; j < sF.getCreatures().get(i).getMemory().length; j++) {
                double[] memory = creatures.get(i).getMemory();
                memory[j] = think[6 + j];
                creatures.get(i).setMemory(memory);
            }

            /*Decrease the foodValue of the creature
            (water multiplies the costs by 2), age is a multiplicator and the foodValue is a multiplicator too*/
            if (blockOnWhichCreatureIs(sF, i).getClass() == Water.class)
                creatures.get(i).setFoodValue(creatures.get(i).getFoodValue() - 2 * (5 + MathForNeuralNetwork.getSumFromArray(think) * creatures.get(i).getAge()));
            else
                creatures.get(i).setFoodValue(creatures.get(i).getFoodValue() - (5 + MathForNeuralNetwork.getSumFromArray(think) * creatures.get(i).getAge()));
        }

        sF.setCreatures(creatures);
        return sF;
    }

    /**
     * Run the update() method on every creature
     *
     * @param sF the old SimulationFrame without of updated creatures
     * @return the new SimulationFrame with updated creatures
     * The update method test if a creature is death and upcrease their age
     */
    private SimulationFrame runUpdateOnCreatures(SimulationFrame sF) {
        ArrayList<Creature> creatures = sF.getCreatures();

        /*Go though every creature*/
        for (int i = 0; i < sF.getCreatures().size(); i++) {
            creatures.get(i).update();
        }

        sF.setCreatures(creatures);

        return sF;
    }

    /**
     * Delete death creatures
     *
     * @param sF the old SimulationFrame without of deleted creatures
     * @return the new SimulationFrame with the deleted creatures
     * if the boolean isDeath is true the creature is deleted form the ArrayList
     */
    private SimulationFrame deleteDeathCreatures(SimulationFrame sF) {
        ArrayList<Creature> creatures = sF.getCreatures();

        int i = 0;

        while (true) {
            /*We are done if the size is bigger or equal to i, because the we went though every creature*/
            if (creatures.size() <= i) {
                break;
            } else {

                /*If the creature is death there is a new creature in this index and we have to repeat*/
                if (creatures.get(i).isDeath()) {
                    creatures.remove(i);
                }

                /*Else we can test the next index*/
                else {
                    i++;
                }
            }
        }

        sF.setCreatures(creatures);

        return sF;
    }


    /**
     * If the number of creatures is less then minValueOfCreatures
     *
     * @param sF the old SimulationFrame with maybe less than minValueOfCreatures of creatures
     * @return the new SimulationFrame with at least minValueOfCreatures of creatures
     * if the there are less than minValueOfCreatures creatures in the
     * ArrayList than add one and call the method again (recursion)
     */
    private SimulationFrame testNumberOfCreatures(SimulationFrame sF, int minValueOfCreatures) {
        ArrayList<Creature> creatures = sF.getCreatures();

        /*Go though every creature*/
        if (creatures.size() < minValueOfCreatures) {
            /*Add a creature with random values*/
            creatures.add(new Creature((int) (Math.random() * sF.getMatrix().length), (int) (Math.random() * sF.getMatrix().length),
                    new int[]{5, 8, 7}, 0, 1));

            sF = testNumberOfCreatures(sF, minValueOfCreatures);
        }

        sF.setCreatures(creatures);
        return sF;
    }

    /**
     * Let one creature eat (take foodValue form a block if this block has foodValue)
     *
     * @param sF the old SimulationFrame with maybe less than minValueOfCreatures of creatures
     * @param indexOfCreature  the index of the creature in the ArrayList
     * @param strongnessOfWill the will to eat (0 = wants eat nothing, 1 = wants to eat much)
     * @return the new SimulationFrame with the creature that eated now
     */
    private SimulationFrame letOneCreatureEat(SimulationFrame sF, int indexOfCreature, double strongnessOfWill) {
        Block[][] matrix = sF.getMatrix();

        Block blockOnWhichCreatureIs = blockOnWhichCreatureIs(sF, indexOfCreature);

        /*If the block is earth and the has enough food*/
        if (blockOnWhichCreatureIs.getClass() == Earth.class && blockOnWhichCreatureIs.getFoodValue() >= 50 * strongnessOfWill) {
            ArrayList<Creature> creatures = sF.getCreatures();

            /*Decrease the foodValue of the block*/
            blockOnWhichCreatureIs.setFoodValue(blockOnWhichCreatureIs.getFoodValue() - 50 * strongnessOfWill);

            /*And upcrease the one on the creature*/
            creatures.get(indexOfCreature).setFoodValue(creatures.get(indexOfCreature).getFoodValue() + 50 * strongnessOfWill);

            sF.setCreatures(creatures);
        }

        return sF;
    }

    /**
     * TODO Mating
     *
     * @param sF               the old SimulationFrame without of the new creature
     * @param indexOfCreature  the index of the creature in the ArrayList
     * @param strongnessOfWill the double if it want to get a child (> 0.5 = true, else = false)
     * @return the old SimulationFrame with the new creature
     */
    private SimulationFrame letOneCreaturePair(SimulationFrame sF, int indexOfCreature, double strongnessOfWill) {
        ArrayList<Creature> creatures = sF.getCreatures();

        /*If the creature has enough energy and wants to get a child*/
        if (sF.getCreatures().get(indexOfCreature).getFoodValue() > 150.0 && strongnessOfWill > 0.5) {
            /*Add the child (mutated version of the mother) to the ArrayList*/
            creatures.add(mutateCreature(creatures.get(indexOfCreature)));

            /*And decrease the energy (foodValue) of the mother by 50*/
            creatures.get(indexOfCreature).setFoodValue(creatures.get(indexOfCreature).getFoodValue() - 50);
        }
        return sF;
    }

    /**
     * Generates the input for a specific creature
     *
     * @param sF              the SimulationFrame of the creature
     * @param indexOfCreature the index of the creature in the ArrayList in the SimulationFrame
     * @return the input for the creature
     * TODO input of feelers
     */
    private double[] generateInputForOneCreature(SimulationFrame sF, int indexOfCreature) {

        /*Without the bias*/
        double[] inputs = new double[4 + (sF.getCreatures().get(indexOfCreature).getNumberOfFeelers() * 4) +
                sF.getCreatures().get(indexOfCreature).getMemory().length];

        /*foodValue of position*/
        inputs[0] = blockOnWhichCreatureIs(sF, indexOfCreature).getFoodValue();

        /*foodValue of creature*/
        inputs[1] = sF.getCreatures().get(indexOfCreature).getFoodValue();

        /*age of creature*/
        inputs[2] = sF.getCreatures().get(indexOfCreature).getAge();

        /*is the creature on water (true = 1; false = 0)*/
        if (blockOnWhichCreatureIs(sF, indexOfCreature).getClass() == Water.class) {
            inputs[3] = 1;
        } else {
            inputs[3] = 0;
        }

        /*the memory (an output from the last frame to simulate a memory)*/
        for (int i = 0; i < sF.getCreatures().get(indexOfCreature).getMemory().length; i++) {
            inputs[4 + i] = sF.getCreatures().get(indexOfCreature).getMemory()[i];
        }

        return inputs;
    }


    /**
     * Mutates one  creature
     * @param creature the creature to mutate
     * @return the mutated creature
     * TODO implement Feelers(-Mutation)/Father
     */
    private Creature mutateCreature(Creature creature) {
        /*Mutate the net*/
        creature.setNet(mutateNet(creature.getNet()));

        /*Mutate the gen*/
        creature.setGen(mutateGen(creature.getGen()));

        /*Set foodValue to 100 (standard for new creatures)*/
        creature.setFoodValue(100);

        /*Set age to 1 (standard for new creatures)*/
        creature.setAge(1);

        return creature;
    }

    /**
     * Mutates a net
     * @param net the old net
     * @return the new mutated net
     * TODO implement HiddenLayerMutation/MemoryNeuronMutation/FeelerMutation/Mutation of not all weights
     */
    private Net mutateNet(Net net) {
        double mutationRate = 0.01;

        for (int i = 0; i < net.weights.length; i++)
            for (int j = 0; j < net.weights.length; j++)
                for (int k = 0; k < net.weights.length; k++)
                    if (Math.random() < 0.5) net.weights[i][j][k] = net.weights[i][j][k] - mutationRate * Math.random();
                    else net.weights[i][j][k] = net.weights[i][j][k] + mutationRate * Math.random();

        return net;
    }

    /**
     * Mutates a gen (propertie of a creature )
     * @param gen the old gen (mother-gen)
     * @return the new mutated gen
     */
    private double[] mutateGen(double[] gen) {
        double mutationRate = 0.01;

        for (int i = 0; i < gen.length; i++)
            if (Math.random() < 0.5)
                gen[i] = gen[i] - mutationRate * Math.random();
            else gen[i] = gen[i] + mutationRate * Math.random();

        return gen;
    }

    /**
     * Find the block on which the creature is
     * @param sF The simulationFrame of the creature
     * @param indexOfCreature the index of the creature in the ArrayList in the SimulationFrame
     * @return the block on which the creature actually is
     */
    private Block blockOnWhichCreatureIs(SimulationFrame sF, int indexOfCreature) {
        Block[] blockArrayOnWhichCreatureIs = null;
        Block blockOnWhichCreatureIs = null;
        int lengthOfOneBlockInPixel = 10;

        /*Find the row in which the creature is*/
        for (int i = 0; i < sF.getMatrix().length; i++) {
            if (sF.getMatrix()[i][0].getPosition().getX() <= sF.getCreatures().get(indexOfCreature).getPosition().getX() &&
                    sF.getMatrix()[i][0].getPosition().getX() + lengthOfOneBlockInPixel >= sF.getCreatures().get(indexOfCreature).getPosition().getX()) {
                blockArrayOnWhichCreatureIs = sF.getMatrix()[i];
            }
        }

        /*Find the exact block on which the block is*/
        for (int i = 0; i < blockArrayOnWhichCreatureIs.length; i++)
            if (blockArrayOnWhichCreatureIs[i].getPosition().getY() <= sF.getCreatures().get(indexOfCreature).getPosition().getY()
                    && blockArrayOnWhichCreatureIs[i].getPosition().getY() + lengthOfOneBlockInPixel >= sF.getCreatures().get(indexOfCreature).getPosition().getY())
                blockOnWhichCreatureIs = blockArrayOnWhichCreatureIs[i];

        return blockOnWhichCreatureIs;
    }
}