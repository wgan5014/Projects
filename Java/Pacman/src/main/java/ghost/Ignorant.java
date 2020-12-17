package ghost;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * Will pursue the player if within 8 tiles distance of the player. Otherwise, pursues the bottom LHS corner of the map
 */
public class Ignorant extends Ghost {
    public Ignorant(PImage display, int x, int y, long vel) {
        super(display, x, y, vel);
    }
    public String toString() {
        return "Ignorant";
    }
    public void setCornerTarget() {
        targetX = 0;
        targetY = 576;
    }
    public void setDefaultTarget(App app) {
        targetX = app.getGame().getWaka().getX();
        targetY = app.getGame().getWaka().getY();
        int euclideanDistSquared = (xCoord - targetX)*(xCoord - targetX) + (yCoord - targetY)*(yCoord - targetY);
        if (euclideanDistSquared > 16384) {
            targetX = 0;
            targetY = 576;
        }
    }
}