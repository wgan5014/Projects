package ghost;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * Class representing non-static entities
 * Ghost and Waka (player class) extend from this class
 */
public class Nonstatic extends Cell {
    protected int xVel, yVel, xFree, yFree, startX, startY;
    protected boolean isDead, isX, collided, freeFound;
    protected PImage defSprite;

    public Nonstatic(PImage spr, int xC, int yC, long vel) {
        super(spr, xC, yC);
        defSprite = spr;
        xVel = yVel = (int) vel; // vel from JSON is assumed to be positive
        collided = isDead = isX = false;
        startX = xC;
        startY = yC;
    }
    /**
     * detects collision of non-static entity with a wall
     * @param   app App object
     */
    public void collision(App app) {
        collided = false;
        for (Cell wall : app.getGame().getWalls()) {
            if (isX) {
                // note: +5 to yCoord to account for vertical offset
                // hardcoded as 5 since this was the adjustment made
                // for both the ghost AND the player
                if (xCoord + xVel < wall.getX() + 16 && xCoord + 16 + xVel > wall.getX() && 
                yCoord + 5 < wall.getY() + 16 && yCoord + 5 + 16 > wall.getY()) {
                    collided = true;
                    break;
                }
            } else {
                if (xCoord  < wall.getX() + 16 && xCoord + 16  > wall.getX() && 
                yCoord + yVel + 5 < wall.getY() + 16 && yCoord + yVel + 5 + 16 > wall.getY()) {
                    collided = true;
                    break;
                }
            }
        }
    }
    /**
     * Returns x-velocity of non-static entity in current movement state
     * @return  int - current x velocity
     */
    public int getXVel() {
        return xVel;
    }
    /**
     * Returns current y-velocity of non-static entity in current movement state
     * @return  int - non-static entity's y-velocity
     */
    public int getYVel() {
        return yVel;
    }
    /**
     * Different getY() for nonstatic
     * Since the nonstatic cells happen to need an offset of 5 
     * W/out this method, would need to add 5 to account for offset
     * @return  int of adjusted y-coordinate for non-static entities
     */
    public int getY() {
        return yCoord + 5;
    }
    /**
     * Returns whether the non-static entity is moving horizontally or not
     * @return  boolean if horizontal movement/not
     */
    public boolean isX() {
        return isX;
    }
    /**
     * return starting x-coord of nonstatic entity
     * @return  int
     */
    public int getStartX() {
        return startX;
    }
    /**
     * return starting y-coord of nonstatic entity
     * @return int - starting Y position
     */
    public int getStartY() {
        return startY;
    }
    /**
     * Draw method for non-static objects
     * Needed because offset required for non-static entities to align on the screen
     * @param   app object 
     */
    public void draw(App app) {
        app.image(this.sprite, this.xCoord - 5, this.yCoord);
    }

    /**
     * Returns the state of collision in a non-static entity
     * @return  boolean - collided or not
     */
    public boolean isCollided() {
        return collided;
    }
}