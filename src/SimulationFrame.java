/**
 * @author lukas_muenzel
 * @version 1.0.1
 */

import java.io.Serializable;
import java.util.ArrayList;

public class SimulationFrame implements Serializable{
    private Block[][] matrix;
    private ArrayList<Creature> creatures = new ArrayList<>();

    public SimulationFrame(){
        matrix = new Block[10][10];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                this.matrix[i][j] = new Block();
            }
        }
    }

    public void updateSimulationFrame(Block[][] matrix, ArrayList<Creature> creatures){
        this.matrix = matrix;
        this.creatures = creatures;
    }

    public Block[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(Block[][] matrix) {
        this.matrix = matrix;
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public void setCreatures(ArrayList<Creature> creatures) {
        this.creatures = creatures;
    }
}
