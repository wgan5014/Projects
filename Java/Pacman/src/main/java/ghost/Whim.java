package ghost;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * Whim subclass of Ghost. Pursues either the two spaces ahead of the player's position based on the Chaser's vector,
 * or pursues the bottom right corner in scatter mode
 */
public class Whim extends Ghost {
    private Chaser chase;
    public Whim(PImage display, int x, int y, long vel) {
        super(display, x, y, vel);
    }
    /**
     * Sets chase of the Whim ghost
     * Guess you could say, the Whim ghost follows the whims of Chaser
     * @param   c   Chaser Ghost object
     */
    public void setChaser(Chaser c) {
        chase = c;
    }
    public void tick(App app, Cell[][] map) {
        assert true;
    }
    public String toString() {
        return "Whim";
    }
}