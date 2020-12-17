
package ghost;

import processing.core.PImage;
import processing.core.PApplet;
import java.util.Random;
/**
 * Pursues the top LHS corner of the map in scatter mode. Otherwise, defaults target to the player's current position
 */

public class Chaser extends Ghost {
    public Chaser(PImage display, int x, int y, long vel) {
        super(display, x, y, vel);
    }
    /**
     * Verify if the current type of ghost is of chaser type specifically; not sub or super-types
     * @return  String "Chaser"
     */
    public String toString() {
        return "Chaser";
    }
    /**
     * Sets the corner target of Chaser ghost to be the top LHS of the map
     */
    public void setCornerTarget() {
        targetX = targetY = 0;
    }
    /**
     * Set default target of Chaser to be the player's current position
     * @param   app App object
     */
    public void setDefaultTarget(App app) {
        targetX = app.getGame().getWaka().getX();
        targetY = app.getGame().getWaka().getY();
    }
}