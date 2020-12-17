package ghost;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * Causes the ghosts to be frightened
 * A fruit but larger, and gives the player an advantage
 */
public class Superfruit extends Fruit {
    public Superfruit(PImage display, int x, int y) {
        super(display, x, y);
    }
    public String toString() {
        return "Superfruit";
    }
}