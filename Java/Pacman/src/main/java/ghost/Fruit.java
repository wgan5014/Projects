package ghost;

import processing.core.PImage;
import processing.core.PApplet;

public class Fruit extends Cell {
    public Fruit(PImage display, int x, int y) {
        super(display, x, y);
    }
    public String toString() {
        return "Fruit";
    }
}