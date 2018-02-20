/**
 * Generates a BlockMatrix with the length of fifty blocks and sets the positions for the blocks
 * @author lukas_muenzel
 * @version 1.0.1
 * TODO
 */
public class BlockMatrixGenerator {
    //TODO
    public static Block[][] generateBlockMatrix(){
        Block[][] blocks = new  Block[50][50];

        for(int i = 0; i < blocks.length; i++)
            for(int j = 0; j < blocks.length; j++)
                blocks[i][j] = new Earth(i * 10, j * 10);

        blocks[0][0] = new Water(0,0);
        return blocks;
    }
}
