/**
 * The Block, used for the BlockMatrix(-Generator) has a foodValue and a position.
 * Implements Serializable because it will be saved.
 * @see Water
 * @see Earth
 * @author lukas_muenzel
 * @version 1.0.1
 */

import java.awt.*;
import java.io.Serializable;

public class Block implements Serializable{
    //TODO do constructor for all

    public double foodValue = 0;

    public java.awt.Point position = new java.awt.Point();

    public double getFoodValue() {
        return foodValue;
    }

    public void setFoodValue(double foodValue) {
        this.foodValue = foodValue;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
