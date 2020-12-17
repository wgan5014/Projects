package ghost;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;

import ghost.*;

public class App extends PApplet {

    public static final int WIDTH = 448;
    public static final int HEIGHT = 576;

    private String file;
    private Game game;
    private Cell[][] map;

    public App() {
        //Set up your objects
        game = new Game();
    }
    
    /**
     * Sets up the conditions for the game: the map used, the amount of lives for the player, framerate etc.
     */
    public void setup() {
        frameRate(60);
        game.setApp(this);
        game.setup(this.file);
        this.map = game.getMap();
    }
    /**
     * Determines size of screen, and the default file which will be used for in-game map
     */
    public void settings() {
        this.file = Parser.mapNameParse(Parser.initialParse("config.json"));
        size(WIDTH, HEIGHT);
    }
    /**
     * Draws in the location of entities post-logic update
     */
    public void draw() { 
        background(0, 0, 0);
        game.tick(this);
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map[i].length; j++) {
                if (!(this.map[i][j] == null)) {
                    map[i][j].draw(this);
                }
            }
        }
        for (Ghost g : game.getGhosts()) {
            if (!g.isDead()) {
                g.draw(this);
                if (Ghost.debug) {
                    line( (float)g.getX(), (float)g.getY(), (float)g.getTargetX(), (float)g.getTargetY());
                    stroke(255, 255, 255);
                }
            }
        }
        game.getWaka().draw(this);
    }
    /**
     * Method activated whenever a key is pressed. Key-pressed passed as argument to player's tick method,
     * therefore updating Waka's position based on user-input
     */
    public void keyPressed() {
        // game's waka logic re-calculated if and ONLY if, the keys are pressed.
        game.getWaka().tick(keyCode, this);
    }
    /**
     * Main-method
     */
    public static void main(String[] args) {
        PApplet.main("ghost.App");
    }
    /**
     * @return App class game object
     */
    public Game getGame() {
        return this.game;
    }
    /**
     * Set file for the App class, which is read in and used the in-game map.
     * @param   filename    String
     */
    public void setFile(String filename) {
        this.file = filename;
    }
}