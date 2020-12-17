package ghost;

import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

/**
 * Responsible for coordinating logic of other classes except for that of App
 */
public class Game {

    protected App app;
    protected Waka w;
    protected Cell[][] map;
    protected ArrayList<Cell> walls, fruits;
    protected ArrayList<Ghost> ghosts;
    protected boolean isOver;

    public Game() {
        ghosts = new ArrayList<>();
        walls = new ArrayList<>();
        fruits = new ArrayList<>();
        isOver = false;
    }
    /**
     * Sets up entities in the game by calling the parseTxtMap() method in Parser class
     */
    public void setup(String filename) {
        try {
            this.map = Parser.parseTxtMap(filename, this.app);
        } catch (NullPointerException e) {
            System.out.println("Map could not be parsed correctly.");
            System.exit(0);
        }
    }

    /**
     * Handle and coordinate Ghost and Waka logic:
     * Move waka, check if ghosts are frightened, set non-static entity positions etc.
     * @param   app App object
     */
    public void tick(App app) {
        this.w.move(this.map, this.app);
        if (isOver) {
            reset();
        } else {
            if (ghosts.size() > 0) {
                if (Ghost.isFrightened) {
                    ++Ghost.frightCounter;
                    if (Ghost.frightCounter == Ghost.frightLength*60) {
                        Ghost.frightCounter = 0;
                        Ghost.isFrightened = false;
                        for (Ghost g : ghosts) {
                            if (g.isDead()) {
                                g.setIsDead(false);
                                g.setX(g.getStartX());
                                g.setY(g.getStartY());
                            }
                        }
                    }
                }
                if (Ghost.modeCounter == 60*Ghost.mlengths[Ghost.modePointer]) {
                    Ghost.isScatter = !Ghost.isScatter; 
                    Ghost.modePointer = (Ghost.modePointer + 1)%Ghost.mlengths.length; 
                    Ghost.modeCounter = 0;
                }
                ++Ghost.modeCounter;
            }
            if (fruits.size() == 0) {
                System.out.println("You win!");
                isOver = true;
            }
            for (Ghost ghost : ghosts) {
                ghost.tick(app, this.map);
            }
            int counter = 0;
            for (int i = 0; i < this.w.getLives(); i++) {
                app.image(app.loadImage("src/main/resources/playerRight.png"), 10 + counter, 545);
                counter += 34;
            }
        }
    }

    /**
     * Occurs when Waka touches ghost
     * Set X and Y coordinates of waka and ghost back to their start positions
     * If player loses all lives, show losing screen, delay 10 seconds and then perform the above
     * @param   app App object
     */
    public void restart(App app) {
        if (w.getLives() <= 0) {
            System.out.println("You lost! :(");
            isOver = true;
        }
        this.w.setDefault(app);
        for (Ghost ghost : ghosts) {
            ghost.setX(ghost.getStartX());
            ghost.setY(ghost.getStartY());
        }
    }
    /**
     * Add ghost to Game object
     * @param   ghost Ghost object
     */
    public void addGhost(Ghost ghost) {
        if (ghost == null) {
            return;
        }
        ghosts.add(ghost);
    }
    /**
     * Returns the Game's map as multi-dim array
     * @return  Cell[][] map
     */
    public Cell[][] getMap() {
        return map;
    }
    /**
     * Return all wall entities in the Game
     * @return  ArrayList<Cell>
     */
    public ArrayList<Cell> getWalls() {
        return walls;
    }
    /**
     * Return all fruit entities in the Game
     * @return  ArrayList<Cell>
     */
    public ArrayList<Cell> getAllFruits() {
        return fruits;
    }
    /**
     * Return all ghost entities in the Game
     * @return  ArrayList<Ghost>
     */
    public ArrayList<Ghost> getGhosts() {
        return ghosts;
    }
    /**
     * Set Waka entity in the Game
     * @param   waka Waka object
     */
    public void setWaka(Waka waka) {
        this.w = waka;
    }
    /**
     * Get Game instances' waka entity
     * @return  Waka object
     */
    public Waka getWaka() {
        return this.w;
    }
    /**
     * Set the application instance in which Game instance is run on
     * @param   app App object
     */
    public void setApp(App app) {
        this.app = app;
    }
    /**
     * Resets the game to the starting state after player has lost all lives
     */
    public void reset() {
        getWalls().clear();
        getAllFruits().clear();
        getGhosts().clear();
        Ghost.modeCounter = 0;
        Ghost.modePointer = 0;
        Ghost.isFrightened = false;
        Ghost.isScatter = true;
        app.setup();
        isOver = false;
    }
}