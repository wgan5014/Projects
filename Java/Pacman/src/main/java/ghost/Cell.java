package ghost;

import processing.core.PImage;
import processing.core.PApplet;
/**
 * Mother class of all entities inside the game
 */
public class Cell {
    protected PImage sprite;
    protected int xCoord, yCoord;
    
    public Cell(PImage spr, int xC, int yC) {
        sprite = spr;
        xCoord = xC;
        yCoord = yC;
    }
    /**
     * gets x-coordinate
     * @return  int xcoord
     */
    public int getX() {
        return xCoord;
    }
    /**
     * gets y-coordinate
     * @return  int ycoord
     */
    public int getY() {
        return yCoord;
    }
    /**
     * sets x-coordinate
     * @param   newX    new x-coord
     */
    public void setX(int newX) {
        xCoord = newX;
    }
    /**
     * sets y-coordinate
     * @param   newY new y-coord
     */
    public void setY(int newY) {
        yCoord = newY;
    }
    /**
     * draws the sprite onto screen
     * @param   app PApplet object
     */
    public void draw(PApplet app) {
        app.image(this.sprite, this.xCoord, this.yCoord);
    }

    public String toString() {
        return "Cell";
    }

    public PImage getSprite() {
        return this.sprite;
    }
}