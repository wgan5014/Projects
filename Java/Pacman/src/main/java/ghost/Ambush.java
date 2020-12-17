package ghost;

import processing.core.PImage;
import processing.core.PApplet;
import java.util.Random;

/**
 * Subclass of ghost, and will pursue one of two targets:
 * Corner target - the top RHS corner of the map
 * Default target - 4 tiles ahead of Waka's current velocity vector
 */
public class Ambush extends Ghost {
    public Ambush(PImage display, int x, int y, long vel) {
        super(display, x, y, vel);
    }
    /**
     * @return  String  "Ambush"
     */
    public String toString() {
        return "Ambush";
    }
    /**
     * Overrides the setCornerTarget method in ghost
     * set corner target for Ambush class ghost
     */
    public void setCornerTarget() {
        targetX = 448;
        targetY = 0;
    }
    /**
     * set default target for Ambush class ghost
     */
    public void setDefaultTarget(App app) {
        Waka wak = app.getGame().getWaka();
        if (wak.isX()) {
            targetX = wak.getX() + 4*16*wak.getXVel();
            if (targetX < 0) {
                targetX = 0;
            }
            targetY = wak.getY();
        } else {
            targetY = wak.getY() + 4*16*wak.getYVel();
            if (targetY < 0) {
                targetY = 0;
            }
            targetX = wak.getX();
        }
    }
}