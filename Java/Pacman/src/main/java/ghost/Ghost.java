package ghost; 

import processing.core.PImage;
import processing.core.PApplet;
import java.util.HashMap;
import java.util.Random;

/**
 * Antagonist to Waka (the player)
 * 4 subtypes: Whim, Ignorant, Chaser, Ambush
 */
public class Ghost extends Nonstatic {
    protected static boolean isScatter = true;
    protected static boolean isFrightened = false;
    protected static boolean debug = false;
    protected int targetX, targetY;
    protected static int frightLength;
    protected static int modeCounter = 0;
    protected static int modePointer = 0;
    protected static int frightCounter = 0;
    protected static long[] mlengths;
    protected HashMap<Integer, Integer> speeds;
    protected int pxv, pyv;
    protected boolean isDead;

    public Ghost(PImage spr, int x, int y, long vel) {
        super(spr, x, y, vel);
        speeds = new HashMap<Integer, Integer>();
        speeds.put(0, Math.abs(xVel)); // right
        speeds.put(1, Math.abs(yVel)); // down
        speeds.put(2, -1*Math.abs(xVel)); // left
        speeds.put(3, -1*Math.abs(yVel)); // up 
        isDead = false;
    }
    /**
     * Will handle the logic of each individual type of ghost
     * Ghost class tick contains the base layout/template for each ghost subclass logic
     * @param   map Game object's cell[][] map
     * @param   app App object 
     */
    public void tick(App app, Cell[][] map) {
        if (hitWaka(app.getGame().getWaka())) {
            app.getGame().restart(app);
        }
        if (Ghost.isScatter) {
            setCornerTarget();
        } else {
            setDefaultTarget(app);
        }
        if (Ghost.isFrightened) {
            sprite = app.loadImage("src/main/resources/frightened.png");
            Random rand = new Random();
            while (collided) {
                int random = rand.nextInt(4);
                setMoveAttributes(random);
                collision(app);
            }
        } else if (!Ghost.isFrightened) {
            sprite = defSprite;
            optimise(app);
        }
        move(app);
    }
    
    /**
     * moves the player
     * @param   app App object
     */
    public void move(App app) {
        collision(app);
        if (!collided) {
            if (isX) {
                xCoord += xVel;
            } else {
                yCoord += yVel;
            }
        }
    }
    
    /**
     * detect if ghost has collided with waka or not
     * Called in tick()
     * @param   waka    Game's Waka object
     * @return  boolean whether collided or not
     */
    public boolean hitWaka(Waka waka) {
        if (this.xCoord == waka.getX() && this.yCoord + 5 == waka.getY()) {
            if (Ghost.isFrightened) {
                isDead = true;
                return false;
            }
            return true;
        }
        return false;
    }
    
    /**
     * Sets the movement attributes of the Ghost
     * @param   move    integer move code to be given so as to map the relevant movement attribute
     */
    public void setMoveAttributes(int move) {
        if (move == 1 || move == 3) {
            yVel = speeds.get(move);
            isX = false;
        } else if (move == 0 || move == 2) {
            xVel = speeds.get(move);
            isX = true;
        }
    }
    
    /**
     * Calculate distance to the target-x-coord, target-y-coord from each possible Ghost entity movement
     * The POSITION of the distance (index) encodes the mapped movement attribute the Ghost will take depending on the distance selected
     * @return  int[]   each element is linear distance.
     */
    public int[] getDistances() {
        // R, D, L, U : 0, 1, 2, 3
        int[] distances = {
            ((xCoord + 1 - targetX)*(xCoord + 1 - targetX) + (yCoord - targetY)*(yCoord - targetY)),
            ((xCoord - targetX)*(xCoord - targetX) + (yCoord + 1 - targetY)*(yCoord + 1 - targetY)),
            ((xCoord - 1 - targetX)*(xCoord - 1 - targetX) + (yCoord - targetY)*(yCoord - targetY)),
            ((xCoord - targetX)*(xCoord - targetX) + (yCoord - 1 - targetY)*(yCoord - 1 - targetY))
        };
        return distances;
    }

    /**
     * Finds least euclidean distance
     * @param   distances   int[] of all linear distances to target - calculated in getDistances() method
     * @return  int[]   holding index of optimal distance, and (if not -1), the index of equidistance
     */
    public int[] findMin(int[] distances) {
        int[] retval = {-2, -1};
        int sd = 100000000;
        for (int i = 0; i < distances.length; i++) {
                // distance of -5 means that "version" of distance is visited
                if (distances[i] < sd && distances[i] != -5) {
                    sd = distances[i];
                    retval[0] = i;
                } else if (distances[i] == sd && distances[i] != -5) {
                    retval[1] = i;
                }
        }
        return retval;
    }
    /**
     * Optimises the direction taken by the Ghost entity
     * based on the euclidean distance from each possible move
     * @param   app object (from Game)
     */
    public void optimise(App app) {
        int optim = -1;
        int[] distances = getDistances();
        int[] idxs = findMin(distances);
        // no equidistant movement found
        if (idxs[1] == -1) {
            optim = idxs[0];
            setMoveAttributes(optim);
        } else if (idxs[1] != -1) {
            optim = idxs[1];
            setMoveAttributes(optim);
        }
        collision(app);
        // if collision occurs, exhaustively search out remaining moves which are optimal
        while (collided) {
            distances[optim] = -5;
            optim = findMin(distances)[0];
            setMoveAttributes(optim);
            collision(app);
        }
    }
    /**
     * Sets modelengths for the Ghosts in the game
     * Modelengths are static
     * i.e. the "activation" of static/not isn't individual to each Ghost
     * @param   modelengths long[] of modelengths parsed from JSON config file
     */
    public static void setModelengths(long[] modelengths) {
        mlengths = modelengths;
    }
    /**
     * Set length of time for ghosts to be frightened
     * @param   frightLength    length of how long the Ghosts will be frightened for when Waka eats superfruit
     */
    public static void setFrightLength(int frightLength) {
        Ghost.frightLength = frightLength;
    }

    /**
     * return x-coordinate of target
     * @return  int - x-coordinate target
     */
    public int getTargetX() {
        return targetX;
    }
    /**
     * return y-coordinate of target
     * @return  int
     */
    public int getTargetY() {
        return targetY;
    }
    /**
     * return fright-lenght
     * @return  int
     */
    public int getFrightLength() {
        return frightLength;
    }
    /**
     * Set state of ghost as alive or dead
     * @param   lifeState   lifestate of ghost i.e. alive or dead
     */
    public void setIsDead(boolean lifeState) {
        isDead = lifeState;
    }
    /**
     * Returns state of ghost as alive/dead
     * @return  boolean alive or dead
     */
    public boolean isDead() {
        return isDead;
    }
    /**
     * Is a base "singleton" method for each Ghost subclass to set its corner target
     * during scatter mode
     */
    public void setCornerTarget() {}
    /**
     * Is a base "singleton" method for each Ghost subclass to set its target
     * when NOT in scatter mode i.e. the default target
     */
    public void setDefaultTarget(App app) {}
}